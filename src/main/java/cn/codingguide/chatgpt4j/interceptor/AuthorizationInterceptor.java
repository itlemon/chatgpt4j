package cn.codingguide.chatgpt4j.interceptor;

import cn.codingguide.chatgpt4j.key.KeySelectorStrategy;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * @author itlemon
 * Created on 2023-04-04
 */
public class AuthorizationInterceptor implements Interceptor {

    /**
     * API KEY List
     */
    private final List<String> apiKey;
    /**
     * API选择策略
     */
    private final KeySelectorStrategy<List<String>, String> keySelectorStrategy;

    public AuthorizationInterceptor(List<String> apiKey,
            KeySelectorStrategy<List<String>, String> keySelectorStrategy) {
        this.apiKey = apiKey;
        this.keySelectorStrategy = keySelectorStrategy;
    }

    @Nonnull
    @Override
    public Response intercept(@Nonnull Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                // 这里设置认证请求头
                .header(Header.AUTHORIZATION.getValue(), "Bearer " + keySelectorStrategy.apply(apiKey))
                .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }

}
