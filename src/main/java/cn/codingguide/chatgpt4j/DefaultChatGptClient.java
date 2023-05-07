package cn.codingguide.chatgpt4j;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;
import java.util.concurrent.TimeUnit;

import cn.codingguide.chatgpt4j.domain.audio.TranscriptionRequest;
import cn.codingguide.chatgpt4j.domain.audio.TranscriptionResponse;
import cn.codingguide.chatgpt4j.domain.audio.TranslationRequest;
import cn.codingguide.chatgpt4j.domain.audio.TranslationResponse;
import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionRequest;
import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionResponse;
import cn.codingguide.chatgpt4j.domain.common.CommonListResponse;
import cn.codingguide.chatgpt4j.domain.completions.CompletionRequest;
import cn.codingguide.chatgpt4j.domain.completions.CompletionResponse;
import cn.codingguide.chatgpt4j.domain.edits.EditRequest;
import cn.codingguide.chatgpt4j.domain.edits.EditResponse;
import cn.codingguide.chatgpt4j.domain.embeddings.EmbeddingRequest;
import cn.codingguide.chatgpt4j.domain.embeddings.EmbeddingResponse;
import cn.codingguide.chatgpt4j.domain.engines.EngineItem;
import cn.codingguide.chatgpt4j.domain.files.FileItem;
import cn.codingguide.chatgpt4j.domain.files.FileResponse;
import cn.codingguide.chatgpt4j.domain.finetune.*;
import cn.codingguide.chatgpt4j.domain.images.ImageEditRequest;
import cn.codingguide.chatgpt4j.domain.images.ImageGenerationRequest;
import cn.codingguide.chatgpt4j.domain.images.ImageResponse;
import cn.codingguide.chatgpt4j.domain.images.ImageVariationRequest;
import cn.codingguide.chatgpt4j.domain.models.Model;
import cn.codingguide.chatgpt4j.domain.moderations.ModerationRequest;
import cn.codingguide.chatgpt4j.domain.moderations.ModerationResponse;
import cn.codingguide.chatgpt4j.exception.ChatGptExceptionCode;
import cn.codingguide.chatgpt4j.interceptor.AuthorizationInterceptor;
import cn.codingguide.chatgpt4j.interceptor.ResponseInterceptor;
import cn.codingguide.chatgpt4j.key.RandomKeySelectorStrategy;
import cn.codingguide.chatgpt4j.utils.ChatGpt4jExceptionUtils;
import cn.codingguide.chatgpt4j.utils.ParamValidator;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
 * @author itlemon
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
                builder.logLevel);

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
     * @param logLevel     日志级别
     * @return 包装后的okHttpClient
     */
    private OkHttpClient warpOkHttpClient(OkHttpClient okHttpClient, Proxy proxy, HttpLoggingInterceptor.Level logLevel) {
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        if (Objects.nonNull(proxy)) {
            // 以显示设置的proxy为准，可以覆盖自定义okHttpClient中的代理
            builder.proxy(proxy);
        }
        if (Objects.nonNull(logLevel)) {
            HttpLoggingInterceptor loggingInterceptor =
                    new HttpLoggingInterceptor(message -> log.info("okHttpClientDetailLog: {}", message));
            loggingInterceptor.setLevel(logLevel);
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
        Single<CommonListResponse<Model>> models = api.models();
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
     * @param image  图片路径
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
        requestBodyMap.put("prompt",
                RequestBody.create(image.getPrompt(), MediaType.parse(ContentType.MULTIPART.toString())));
        buildImageMultipartBodyCommonMap(requestBodyMap, image.getN().toString(), image.getSize(),
                image.getResponseFormat(), image.getUser());

        Single<ImageResponse> imageResponse = api.imageEdits(imageMultipartBody, maskMultipartBody, requestBodyMap);
        return imageResponse.blockingGet();
    }

    /**
     * 创建给定图像的变体
     *
     * @param image 图片参数
     * @return 重做后的图片
     */
    public ImageResponse imageVariations(ImageVariationRequest image) {
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

    /**
     * 向量计算：集合文本
     *
     * @param input 文本
     * @return 计算结果
     */
    public EmbeddingResponse embeddings(String input) {
        EmbeddingRequest embedding = EmbeddingRequest.newBuilder().addInput(input).build();
        return embeddings(embedding);
    }

    /**
     * 向量计算：集合文本
     *
     * @param input 文本集合
     * @return 计算结果
     */
    public EmbeddingResponse embeddings(List<String> input) {
        EmbeddingRequest embedding = EmbeddingRequest.newBuilder().addAllInput(input).build();
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
     * 语音转文字
     *
     * @param audioFile 语音文件，格式必须是： mp3, mp4, mpeg, mpga, m4a, wav, or webm 其中之一
     * @return 转换结果
     */
    public TranscriptionResponse speechToTextTranscriptions(String audioFile) {
        TranscriptionRequest transcriptionRequest = TranscriptionRequest.newBuilder().file(audioFile).build();
        return speechToTextTranscriptions(transcriptionRequest);
    }

    /**
     * 语音转文字
     *
     * @param transcription 自定义参数
     * @return 转换结果
     */
    public TranscriptionResponse speechToTextTranscriptions(TranscriptionRequest transcription) {
        // 这里做一些基础参数校验，其他的参数基本依赖OPENAI的校验，因为对于部分空值，在构建请求参数的时候就会出现异常
        ParamValidator.validateTranscriptionRequest(transcription);
        // 构建文件
        MultipartBody.Part multipartBody = buildAudioMultipartBodyPart(transcription.getFile());

        // 构建其他参数
        Map<String, RequestBody> requestBodyMap = Maps.newHashMapWithExpectedSize(5);
        buildAudioMultipartBodyCommonMap(requestBodyMap, transcription.getModel(), transcription.getPrompt(),
                transcription.getResponseFormat(), transcription.getTemperature(), transcription.getLanguage());

        Single<TranscriptionResponse> speechToTextTranscriptions =
                api.speechToTextTranscriptions(multipartBody, requestBodyMap);
        return speechToTextTranscriptions.blockingGet();
    }

    /**
     * 语音翻译成文字，目前仅仅支持翻译英文
     *
     * @param audioFile 语音文件，格式必须是： mp3, mp4, mpeg, mpga, m4a, wav, or webm 其中之一
     * @return 转换结果
     */
    public TranslationResponse speechToTextTranslations(String audioFile) {
        TranslationRequest translationRequest = TranslationRequest.newBuilder().file(audioFile).build();
        return speechToTextTranslations(translationRequest);
    }

    /**
     * 语音翻译成文字，目前仅仅支持翻译英文
     *
     * @param translation 自定义参数
     * @return 转换结果
     */
    public TranslationResponse speechToTextTranslations(TranslationRequest translation) {
        // 这里做一些基础参数校验，其他的参数基本依赖OPENAI的校验，因为对于部分空值，在构建请求参数的时候就会出现异常
        ParamValidator.validateTranslationRequest(translation);
        // 构建文件
        MultipartBody.Part multipartBody = buildAudioMultipartBodyPart(translation.getFile());

        // 构建其他参数
        Map<String, RequestBody> requestBodyMap = Maps.newHashMapWithExpectedSize(4);
        buildAudioMultipartBodyCommonMap(requestBodyMap, translation.getModel(), translation.getPrompt(),
                translation.getResponseFormat(), translation.getTemperature(), null);

        Single<TranslationResponse> speechToTextTranslations =
                api.speechToTextTranslations(multipartBody, requestBodyMap);
        return speechToTextTranslations.blockingGet();
    }

    /**
     * 获取文件列表
     *
     * @return 文件列表
     */
    public FileResponse files() {
        return api.files().blockingGet();
    }

    /**
     * 上传文件，上传的文件必须是一个JSONL文件，比如一个test.txt，它的内容，必须是每一行都是一个json对象，json中有两个字段：
     * 分别是prompt，它是必需项，还有一个是completion，它是可选项。prompt 键指定了模型应该如何开始生成文本，而 completion 键则指定了模型应该生成多少文本。
     * 例如，文本文件test.txt，它的内容是：
     * <pre>
     * {@code {"prompt": "Hello, my name is", "completion": ""}}
     * {@code {"prompt": "What is the meaning of life?", "completion": ""}}
     * </pre>
     *
     * @param filePath 文件路径
     * @return 上传结果
     */
    public FileItem uploadFile(String filePath) {
        return uploadFile(filePath, "fine-tune");
    }

    /**
     * 上传文件，上传的文件必须是一个JSONL文件，比如一个test.txt，它的内容，必须是每一行都是一个json对象，json中有两个字段：
     * 分别是prompt，它是必需项，还有一个是completion，它是可选项。prompt 键指定了模型应该如何开始生成文本，而 completion 键则指定了模型应该生成多少文本。
     * 例如，文本文件test.txt，它的内容是：
     * <pre>
     * {@code {"prompt": "Hello, my name is", "completion": ""}}
     * {@code {"prompt": "What is the meaning of life?", "completion": ""}}
     * </pre>
     *
     * @param filePath 文件路径
     * @param purpose  文件用途：官网默认值是fine-tune
     * @return 上传结果
     */
    public FileItem uploadFile(String filePath, String purpose) {
        ParamValidator.validateFile(filePath);
        // 创建 RequestBody，用于封装构建RequestBody
        File file = FileUtil.file(filePath);
        RequestBody fileBody = RequestBody.create(file, MediaType.parse(ContentType.MULTIPART.toString()));
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
        RequestBody purposeBody = RequestBody.create(purpose, MediaType.parse(ContentType.MULTIPART.toString()));
        Single<FileItem> uploadFileResponse = api.uploadFile(multipartBody, purposeBody);
        return uploadFileResponse.blockingGet();
    }

    /**
     * 删除文件
     *
     * @param fileId 文件ID
     * @return 删除结果
     */
    public FileItem deleteFile(String fileId) {
        return api.deleteFile(fileId).blockingGet();
    }

    /**
     * 检索文件
     *
     * @param fileId 文件ID
     * @return 检索结果
     */
    public FileItem retrieveFile(String fileId) {
        return api.retrieveFile(fileId).blockingGet();
    }

    /**
     * 检索文件内容（有钱人专享接口）
     *
     * @param fileId 文件ID
     * @return 检索结果
     */
    public ResponseBody retrieveFileContent(String fileId) {
        return api.retrieveFileContent(fileId).blockingGet();
    }

    /**
     * 创建微调模型
     *
     * @param trainingFileId 训练文件ID
     * @return 微调结果
     */
    public FineTuneResponse fineTune(String trainingFileId) {
        Single<FineTuneResponse> fineTuneResponse =
                api.fineTune(FineTuneRequest.newBuilder().trainingFile(trainingFileId).build());
        return fineTuneResponse.blockingGet();
    }

    /**
     * 根据已有模型进行微调创建新模型
     *
     * @param fineTune 创建参数
     * @return 创建结果
     */
    public FineTuneResponse fineTune(FineTuneRequest fineTune) {
        Single<FineTuneResponse> fineTuneResponse = api.fineTune(fineTune);
        return fineTuneResponse.blockingGet();
    }

    /**
     * 获取微调作业列表
     *
     * @return 微调作业列表
     */
    public CommonListResponse<FineTuneResponse> fineTunes() {
        Single<CommonListResponse<FineTuneResponse>> fineTunes = api.fineTunes();
        return fineTunes.blockingGet();
    }

    /**
     * 检索微调作业
     *
     * @param fineTuneId 微调作业ID
     * @return 作业详情
     */
    public FineTuneResponse retrieveFineTune(String fineTuneId) {
        Single<FineTuneResponse> fineTune = api.retrieveFineTune(fineTuneId);
        return fineTune.blockingGet();
    }

    /**
     * 取消微调作业
     *
     * @param fineTuneId 微调作业ID
     * @return 取消结果
     */
    public FineTuneResponse cancelFineTune(String fineTuneId) {
        Single<FineTuneResponse> fineTune = api.cancelFineTune(fineTuneId);
        return fineTune.blockingGet();
    }

    /**
     * 微调作业事件列表
     *
     * @param fineTuneId 微调作业ID
     * @return 事件列表
     */
    public CommonListResponse<FineTuneEvent> fineTuneEvents(String fineTuneId) {
        Single<CommonListResponse<FineTuneEvent>> events = api.fineTuneEvents(fineTuneId);
        return events.blockingGet();
    }

    /**
     * 删除微调作业模型，只能删除自己的组织内的作业模型
     *
     * @param model 模型ID
     * @return 删除结果
     */
    public FineTuneDeleteResponse deleteFineTuneModel(String model) {
        Single<FineTuneDeleteResponse> delete = api.deleteFineTuneModel(model);
        return delete.blockingGet();
    }

    /**
     * 文本审核与分类，主要用于检测文本是否歧视、自残等内容
     *
     * @param text 待检测文本
     * @return 审核分类结果
     */
    public ModerationResponse moderations(String text) {
        return moderations(ModerationRequest.newBuilder().addInput(text).build());
    }

    /**
     * 文本审核与分类，主要用于检测文本是否歧视、自残等内容
     *
     * @param text 待检测文本
     * @return 审核分类结果
     */
    public ModerationResponse moderations(List<String> text) {
        return moderations(ModerationRequest.newBuilder().addAllInput(text).build());
    }

    /**
     * 文本审核与分类，主要用于检测文本是否歧视、自残等内容
     *
     * @param moderation 请求参数
     * @return 审核分类结果
     */
    public ModerationResponse moderations(ModerationRequest moderation) {
        Single<ModerationResponse> moderations = api.moderations(moderation);
        return moderations.blockingGet();
    }

    /**
     * 引擎列表，OPENAI已废弃该接口（Please use their replacement, Models, instead.）
     *
     * @return 引擎列表
     */
    @Deprecated
    public CommonListResponse<EngineItem> engines() {
        Single<CommonListResponse<EngineItem>> engines = api.engines();
        return engines.blockingGet();
    }

    /**
     * 检索引擎，OPENAI已废弃该接口（Please use their replacement, Models, instead.）
     *
     * @param engineId 引擎ID
     * @return 引擎
     */
    @Deprecated
    public EngineItem engine(String engineId) {
        Single<EngineItem> engine = api.engine(engineId);
        return engine.blockingGet();
    }

    private MultipartBody.Part buildAudioMultipartBodyPart(String audioPath) {
        File audioFile = FileUtil.file(audioPath);
        RequestBody fileBody = RequestBody.create(audioFile, MediaType.parse(ContentType.MULTIPART.toString()));
        return MultipartBody.Part.createFormData("file", audioFile.getName(), fileBody);
    }

    private void buildAudioMultipartBodyCommonMap(Map<String, RequestBody> requestBodyMap, String model, String prompt,
                                                  String responseFormat, Double temperature, String language) {
        // 构建模型
        requestBodyMap.put("model", RequestBody.create(model, MediaType.parse(ContentType.MULTIPART.toString())));

        // 其他参数
        if (StrUtil.isNotBlank(prompt)) {
            requestBodyMap.put("prompt", RequestBody.create(prompt, MediaType.parse(ContentType.MULTIPART.toString())));
        }
        if (StrUtil.isNotBlank(responseFormat)) {
            requestBodyMap.put("response_format",
                    RequestBody.create(responseFormat, MediaType.parse(ContentType.MULTIPART.toString())));
        }
        if (Objects.nonNull(temperature)) {
            requestBodyMap.put("temperature",
                    RequestBody.create(String.valueOf(temperature), MediaType.parse(ContentType.MULTIPART.toString())));
        }
        if (StrUtil.isNotBlank(language)) {
            requestBodyMap.put("language",
                    RequestBody.create(language, MediaType.parse(ContentType.MULTIPART.toString())));
        }
    }

    private MultipartBody.Part buildImageMultipartBodyPart(String paramName, String imagePath) {
        File imageFile = FileUtil.file(imagePath);
        RequestBody imageBody = RequestBody.create(imageFile, MediaType.parse(ContentType.MULTIPART.toString()));
        return MultipartBody.Part.createFormData(paramName, imageFile.getName(), imageBody);
    }

    private void buildImageMultipartBodyCommonMap(Map<String, RequestBody> requestBodyMap, String n, String size,
                                                  String responseFormat, String user) {
        MediaType mediaType = MediaType.parse(ContentType.MULTIPART.toString());
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

        private HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.NONE;

        private Builder() {
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
         * @param ip   代理IP
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
         * @param ip   代理IP
         * @param port 代理端口
         * @return Builder
         */
        public Builder proxySocks(String ip, int port) {
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
         * 请求过程中的日志级别，分别有NONE、BASIC、HEADERS、BODY，分别无日志、基础日志、请求头日志、请求体日志，测试阶段建议开启BODY日志，以便观察请求过程
         *
         * @param logLevel 日志级别
         * @return Builder
         */
        public Builder logLevel(HttpLoggingInterceptor.Level logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public DefaultChatGptClient build() {
            return new DefaultChatGptClient(this);
        }
    }

}
