package cn.codingguide.chatgpt4j;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionRequest;
import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionResponse;
import cn.codingguide.chatgpt4j.domain.completions.CompletionRequest;
import cn.codingguide.chatgpt4j.domain.completions.CompletionResponse;
import cn.codingguide.chatgpt4j.domain.edits.EditRequest;
import cn.codingguide.chatgpt4j.domain.edits.EditResponse;
import cn.codingguide.chatgpt4j.domain.embeddings.EmbeddingRequest;
import cn.codingguide.chatgpt4j.domain.embeddings.EmbeddingResponse;
import cn.codingguide.chatgpt4j.domain.images.ImageEditRequest;
import cn.codingguide.chatgpt4j.domain.images.ImageGenerationRequest;
import cn.codingguide.chatgpt4j.domain.images.ImageResponse;
import cn.codingguide.chatgpt4j.domain.images.ImageVariation;
import cn.codingguide.chatgpt4j.domain.models.Model;
import cn.codingguide.chatgpt4j.domain.models.ModelResponse;
import cn.codingguide.chatgpt4j.exception.ChatGptExceptionCode;
import cn.codingguide.chatgpt4j.interceptor.AuthorizationInterceptor;
import cn.codingguide.chatgpt4j.interceptor.ResponseInterceptor;
import cn.codingguide.chatgpt4j.key.RandomKeySelectorStrategy;
import cn.codingguide.chatgpt4j.utils.ChatGpt4jExceptionUtils;
import cn.codingguide.chatgpt4j.utils.ParamValidator;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Maps;

import cn.codingguide.chatgpt4j.api.OpenAiApi;
import cn.codingguide.chatgpt4j.key.KeySelectorStrategy;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 默认的ChatGPT客户端
 *
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-04
 */
public class DefaultChatGptClient {

    private static final Log log = LogFactory.get();

    /**
     * API KEY列表，不可为null，不可为空
     */
    @NotNull
    private final List<String> apiKeys;

    /**
     * API Host，默认为：https://api.openai.com/，可设置代理
     */
    private final String apiHost;

    /**
     * API KEY的选择器，默认实现是从列表中随机选择，用户可自定义选择逻辑
     */
    private final KeySelectorStrategy<List<String>, String> keySelectorStrategy;

    /**
     * okHttpClient，支持自定义
     */
    private final OkHttpClient okHttpClient;

    /**
     * API
     */
    private final OpenAiApi api;

    private DefaultChatGptClient(Builder builder) {
        // 设置apiKeys
        ChatGpt4jExceptionUtils.isTrue(CollectionUtil.isEmpty(builder.apiKeys))
                .throwMessage(ChatGptExceptionCode.OPEN_AI_INVALID_REQUEST_ERROR, "Api keys must be not empty.");
        apiKeys = builder.apiKeys;

        // 设置api代理host
        String apiHostTemp;
        apiHost = StrUtil.isBlank(apiHostTemp = builder.apiHost) ? OpenAiApi.OPEN_AI_HOST : warpApiHost(apiHostTemp);

        // 设置API KEY选择策略
        KeySelectorStrategy<List<String>, String> apiSelectorStrategyTemp;
        keySelectorStrategy = Objects.isNull(apiSelectorStrategyTemp = builder.keySelectorStrategy) ?
                              new RandomKeySelectorStrategy() : apiSelectorStrategyTemp;

        // 设置okHttpClient
        okHttpClient = warpOkHttpClient(Objects.isNull(builder.okHttpClient) ? okHttpClient() : builder.okHttpClient,
                builder.proxy,
                builder.enableHttpDetailLog);

        // 设置OpenAiApi
        api = new Retrofit.Builder()
                .baseUrl(apiHost)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenAiApi.class);
    }

    /**
     * SDK 默认的okHttpClient
     *
     * @return okHttpClient
     */
    private OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                // 默认客户端没有代理
                .proxy(Proxy.NO_PROXY)
                .addInterceptor(new ResponseInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 检查用户设置的ApiHost是否以 / 结尾
     *
     * @param apiHost apiHost
     * @return apiHost
     */
    private String warpApiHost(String apiHost) {
        return apiHost.endsWith("/") ? apiHost : apiHost + "/";
    }

    /**
     * 将用户自定义的okHttpClient包装一下，主要是设置了请求头
     *
     * @param okHttpClient 用户自定义okHttpClient
     * @param enableHttpDetailLog 是否开启Http请求的详细日志
     * @return 包装后的okHttpClient
     */
    private OkHttpClient warpOkHttpClient(OkHttpClient okHttpClient, Proxy proxy, boolean enableHttpDetailLog) {
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        if (!Objects.isNull(proxy)) {
            // 以显示设置的proxy为准，可以覆盖自定义okHttpClient中的代理
            builder.proxy(proxy);
        }
        if (enableHttpDetailLog) {
            HttpLoggingInterceptor loggingInterceptor =
                    new HttpLoggingInterceptor(message -> log.info("okHttpClientDetailLog: {}", message));
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        return builder
                .addInterceptor(new AuthorizationInterceptor(apiKeys, keySelectorStrategy))
                .build();
    }

    /**
     * 获取Open AI模型列表
     *
     * @return 模型列表
     */
    public List<Model> models() {
        Single<ModelResponse> models = api.models();
        return models.blockingGet().getData();
    }

    /**
     * 获取OpenAI模型详情
     *
     * @param id 模型ID
     * @return 模型详情
     */
    public Model model(@NotNull String id) {
        ChatGpt4jExceptionUtils.isTrue(StrUtil.isBlank(id))
                .throwMessage(ChatGptExceptionCode.OPEN_AI_INVALID_REQUEST_ERROR, "Model ID is a required parameter.");
        Single<Model> model = api.model(id);
        return model.blockingGet();
    }

    /**
     * 问答接口，每次文档需要将上文带上，需要注意的是，文本的数量不能超限，本接口支持持续对话，具体用法可参考测试用例
     *
     * @param completion 问答请求参数
     * @return 问答结果
     */
    public CompletionResponse completions(@NotNull CompletionRequest completion) {
        Single<CompletionResponse> completions = api.completions(completion);
        return completions.blockingGet();
    }

    /**
     * 简易问答接口，单次提问可使用该接口，具体用法可参考测试用例
     *
     * @param completion 具体问题
     * @return 问答结果
     */
    public CompletionResponse completions(@NotNull String completion) {
        CompletionRequest completionRequest = CompletionRequest.newBuilder().prompt(completion).build();
        Single<CompletionResponse> completions = api.completions(completionRequest);
        return completions.blockingGet();
    }

    /**
     * 为给定的聊天对话创建模型响应，默认模型：gpt-3.5-turbo，和官网chat窗口问答一致
     *
     * @param chatCompletion 请求参数
     * @return 返回问答内容
     */
    public ChatCompletionResponse chatCompletions(ChatCompletionRequest chatCompletion) {
        Single<ChatCompletionResponse> chatCompletionResponse = api.chatCompletion(chatCompletion);
        return chatCompletionResponse.blockingGet();
    }

    /**
     * 文本修改、编辑
     *
     * @param edit 请求参数
     * @return 编辑返回体
     */
    public EditResponse edit(EditRequest edit) {
        Single<EditResponse> edits = api.edits(edit);
        return edits.blockingGet();
    }

    /**
     * 根据描述生成图片，可以定义图片的返回格式，url或者b64_json
     *
     * @param image 请求参数
     * @return 图片列表
     */
    public ImageResponse imageGenerations(ImageGenerationRequest image) {
        Single<ImageResponse> imageGenerations = api.imageGenerations(image);
        return imageGenerations.blockingGet();
    }

    /**
     * 根据描述生成图片，图片默认以URL的形式返回
     *
     * @param image 请求参数
     * @return 图片列表
     */
    public ImageResponse imageGenerations(String image) {
        return imageGenerations(ImageGenerationRequest.newBuilder().prompt(image).build());
    }

    /**
     * 根据描述编辑图片
     *
     * @param image 图片路径
     * @param prompt 描述
     * @return 编辑后的图片
     */
    public ImageResponse imageEdits(String image, String prompt) {
        return imageEdits(ImageEditRequest.newBuilder().image(image).prompt(prompt).build());
    }

    /**
     * 编辑图片
     *
     * @param image 图片参数
     * @return 编辑后的图片
     */
    public ImageResponse imageEdits(ImageEditRequest image) {
        // 这里做一些基础参数校验，其他的参数基本依赖OPENAI的校验，因为对于部分空值，在构建请求参数的时候就会出现异常
        ParamValidator.validateImageEditRequest(image.getImage(), image.getMask());
        // 构建请求参数
        MultipartBody.Part imageMultipartBody = buildImageMultipartBodyPart("image", image.getImage());

        MultipartBody.Part maskMultipartBody = null;
        if (StrUtil.isNotBlank(image.getMask())) {
            maskMultipartBody = buildImageMultipartBodyPart("mask", image.getMask());
        }

        Map<String, RequestBody> requestBodyMap = Maps.newHashMapWithExpectedSize(5);
        requestBodyMap.put("prompt", RequestBody.create(image.getPrompt(), MediaType.parse("multipart/form-data")));
        buildImageMultipartBodyCommonMap(requestBodyMap, image.getN().toString(), image.getSize(),
                image.getResponseFormat(), image.getUser());

        Single<ImageResponse> imageResponse = api.imageEdits(imageMultipartBody, maskMultipartBody, requestBodyMap);
        return imageResponse.blockingGet();
    }

    /**
     * 向量计算：集合文本
     *
     * @param input 文本集合
     * @return 计算结果
     */
    public EmbeddingResponse embeddings(List<String> input) {
        EmbeddingRequest embedding = EmbeddingRequest.newBuilder().input(input).build();
        return embeddings(embedding);
    }

    /**
     * Creates an embedding vector representing the input text.
     *
     * @param embedding 文本计算参数
     * @return 计算结果
     */
    public EmbeddingResponse embeddings(EmbeddingRequest embedding) {
        Single<EmbeddingResponse> embeddings = api.embeddings(embedding);
        return embeddings.blockingGet();
    }

    /**
     * 创建给定图像的变体
     *
     * @param image 图片参数
     * @return 重做后的图片
     */
    public ImageResponse imageVariations(ImageVariation image) {
        // 这里做一些基础参数校验，其他的参数基本依赖OPENAI的校验，因为对于部分空值，在构建请求参数的时候就会出现异常
        ParamValidator.validateImageEditRequest(image.getImage(), null);
        // 构建请求参数
        MultipartBody.Part imageMultipartBody = buildImageMultipartBodyPart("image", image.getImage());

        Map<String, RequestBody> requestBodyMap = Maps.newHashMapWithExpectedSize(4);
        buildImageMultipartBodyCommonMap(requestBodyMap, image.getN().toString(), image.getSize(),
                image.getResponseFormat(), image.getUser());

        Single<ImageResponse> imageResponse = api.imageVariations(imageMultipartBody, requestBodyMap);
        return imageResponse.blockingGet();
    }

    private MultipartBody.Part buildImageMultipartBodyPart(String paramName, String imagePath) {
        File imageFile = FileUtil.file(imagePath);
        RequestBody imageBody = RequestBody.create(imageFile, MediaType.parse("multipart/form-data"));
        return MultipartBody.Part.createFormData(paramName, imageFile.getName(), imageBody);
    }

    private void buildImageMultipartBodyCommonMap(Map<String, RequestBody> requestBodyMap, String n, String size,
            String responseFormat, String user) {
        MediaType mediaType = MediaType.parse("multipart/form-data");
        requestBodyMap.put("n", RequestBody.create(n, mediaType));
        requestBodyMap.put("size", RequestBody.create(size, mediaType));
        requestBodyMap.put("response_format", RequestBody.create(responseFormat, mediaType));
        if (StrUtil.isNotBlank(user)) {
            requestBodyMap.put("user", RequestBody.create(user, mediaType));
        }
    }

    /**
     * Builder构造器
     *
     * @return Builder对象
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private List<String> apiKeys;

        private String apiHost;

        private Proxy proxy;

        private OkHttpClient okHttpClient;

        private KeySelectorStrategy<List<String>, String> keySelectorStrategy;

        private boolean enableHttpDetailLog;

        public Builder() {
        }

        /**
         * 这里填写OpenAI的API KEY列表，必填项
         *
         * @param apiKeys apiKeys
         * @return Builder
         */
        public Builder apiKeys(@NotNull List<String> apiKeys) {
            this.apiKeys = apiKeys;
            return this;
        }

        /**
         * API代理地址，非必填项，如果没有代理，就无需填写
         *
         * @param apiHost API代理地址
         * @return Builder
         */
        public Builder apiHost(String apiHost) {
            this.apiHost = apiHost;
            return this;
        }

        /**
         * http代理，国内无法直接访问，需要代理，和socks代理二选一即可，非必填项
         *
         * @param ip 代理IP
         * @param port 代理端口
         * @return Builder
         */
        public Builder proxyHttp(String ip, int port) {
            this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            return this;
        }

        /**
         * socks代理，国内无法直接访问，需要代理，和http代理二选一即可，非必填项
         *
         * @param ip 代理IP
         * @param port 代理端口
         * @return Builder
         */
        public Builder proxySocks5(String ip, int port) {
            this.proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(ip, port));
            return this;
        }

        /**
         * 自定义okHttpClient，非必填项
         *
         * @param okHttpClient okHttpClient
         * @return Builder
         */
        public Builder okHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }

        /**
         * 从 API KEY 列表中选择Key的策略，默认是随机选择，非必填项
         *
         * @param keySelectorStrategy Key选择策略
         * @return Builder
         */
        public Builder keySelectorStrategy(KeySelectorStrategy<List<String>, String> keySelectorStrategy) {
            this.keySelectorStrategy = keySelectorStrategy;
            return this;
        }

        /**
         * 是否开启详细的请求日志记录
         *
         * @param enableHttpDetailLog 是否开启详细的请求日志记录
         * @return Builder
         */
        public Builder enableHttpDetailLog(boolean enableHttpDetailLog) {
            this.enableHttpDetailLog = enableHttpDetailLog;
            return this;
        }

        public DefaultChatGptClient build() {
            return new DefaultChatGptClient(this);
        }
    }

}
