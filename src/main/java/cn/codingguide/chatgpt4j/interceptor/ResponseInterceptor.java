package cn.codingguide.chatgpt4j.interceptor;

import cn.codingguide.chatgpt4j.domain.common.OpenAiErrorResponse;
import cn.codingguide.chatgpt4j.exception.ChatGpt4jException;
import cn.codingguide.chatgpt4j.exception.ChatGptExceptionMsg;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

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
        if (!response.isSuccessful()) {
            // 请求失败
            int responseCode = response.code();
            if (responseCode == ChatGptExceptionMsg.OPEN_AI_AUTHENTICATION_ERROR.code()
                    || responseCode == ChatGptExceptionMsg.OPEN_AI_NOT_FOUND_ERROR.code()
                    || responseCode == ChatGptExceptionMsg.OPEN_AI_RESOURCE_LIMIT_ERROR.code()
                    || responseCode == ChatGptExceptionMsg.OPENAI_SERVER_ERROR.code()) {
                ResponseBody body = response.body();
                if (!Objects.isNull(body)) {
                    OpenAiErrorResponse openAiErrorResponse = JSONUtil.toBean(body.string(), OpenAiErrorResponse.class);
                    log.error("request url: {} fail, openAiErrorResponse: {}", request.url(), openAiErrorResponse);
                    throw new ChatGpt4jException(openAiErrorResponse.getError().getMessage());
                }
                log.error("request url: {} fail, response code: {}", request.url(), responseCode);
                throw new ChatGpt4jException(responseCode, "request url: " + request.url() + " fail");
            }
        }
        return response;
    }
}
