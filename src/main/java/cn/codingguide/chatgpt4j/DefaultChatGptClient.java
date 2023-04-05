package cn.codingguide.chatgpt4j;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import cn.codingguide.chatgpt4j.domain.models.Model;
import cn.codingguide.chatgpt4j.domain.models.ModelResponse;
import cn.codingguide.chatgpt4j.exception.ChatGpt4jException;
import cn.codingguide.chatgpt4j.exception.ChatGptExceptionMsg;
import cn.codingguide.chatgpt4j.interceptor.AuthorizationInterceptor;
import cn.codingguide.chatgpt4j.interceptor.ResponseInterceptor;
import cn.codingguide.chatgpt4j.key.RandomKeySelectorStrategy;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.reactivex.Single;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;

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
        if (CollectionUtil.isEmpty(builder.apiKeys)) {
            throw new ChatGpt4jException(ChatGptExceptionMsg.API_KEY_LIST_NOT_EMPTY);
        }
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
     * @param okHttpClient        用户自定义okHttpClient
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
        if (StrUtil.isBlank(id)) {
            throw new ChatGpt4jException(ChatGptExceptionMsg.INVALID_PARAM_ERROR);
        }
        Single<Model> model = api.model(id);
        return model.blockingGet();
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
