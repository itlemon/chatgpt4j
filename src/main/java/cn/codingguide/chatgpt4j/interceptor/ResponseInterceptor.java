package cn.codingguide.chatgpt4j.interceptor;

import cn.codingguide.chatgpt4j.domain.common.OpenAiErrorResponse;
import cn.codingguide.chatgpt4j.exception.ChatGptExceptionCode;
import cn.codingguide.chatgpt4j.utils.ChatGpt4jExceptionUtils;
import cn.codingguide.chatgpt4j.utils.EnumUtils;
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
 * @author itlemon
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
            ChatGptExceptionCode responseCode = ChatGptExceptionCode.parseFromCode(response.code());
            if (EnumUtils.isIn(responseCode, ChatGptExceptionCode.OPEN_AI_INVALID_REQUEST_ERROR,
                    ChatGptExceptionCode.OPEN_AI_AUTHENTICATION_ERROR, ChatGptExceptionCode.OPEN_AI_NOT_FOUND_ERROR,
                    ChatGptExceptionCode.OPEN_AI_RESOURCE_LIMIT_ERROR, ChatGptExceptionCode.OPENAI_SERVER_ERROR)) {
                // 说明是OpenAI返回的错误
                ResponseBody body = response.body();
                if (Objects.nonNull(body)) {
                    OpenAiErrorResponse openAiErrorResponse = JSONUtil.toBean(body.string(), OpenAiErrorResponse.class);
                    log.error("request url: {} fail, openAiErrorResponse: {}", request.url(), openAiErrorResponse);
                    ChatGpt4jExceptionUtils.isTrue(true)
                            .throwMessage(responseCode, openAiErrorResponse.getError().getMessage());
                }
                log.error("request url: {} fail, response code: {}", request.url(), responseCode);
                ChatGpt4jExceptionUtils.isTrue(true)
                        .throwMessage(responseCode, "request url: " + request.url() + " fail");
            } else {
                // 非预期错误
                log.error("unexpected error code. request url: {} fail.", request.url());
                ChatGpt4jExceptionUtils.isTrue(true)
                        .throwMessage(responseCode, "unexpected error code. request url: " + request.url() + " fail");
            }
        }
        return response;
    }
}
