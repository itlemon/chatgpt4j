package cn.codingguide.chatgpt4j.interceptor;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-04
 */
public class ResponseInterceptor implements Interceptor {

    private static final Log log = LogFactory.get();

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        log.info("Open AI response: {}", response.toString());
        return response;
    }
}
