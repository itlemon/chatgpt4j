package cn.codingguide.chatgpt4j.utils;

import cn.codingguide.chatgpt4j.exception.ChatGpt4jException;
import cn.codingguide.chatgpt4j.exception.ThrowExceptionFunction;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * @author itlemon
 * Created on 2023-04-23
 */
public class ChatGpt4jExceptionUtils {

    private static final Log log = LogFactory.get();

    private ChatGpt4jExceptionUtils() {
    }

    public static ThrowExceptionFunction isTrue(boolean expression) {
        return (code, message) -> {
            if (expression) {
                log.error("ChatGpt4jExceptionUtils found exception, code: {}-{}, message: {}.", code, code.code(), message);
                throw new ChatGpt4jException(code, message);
            }
        };
    }


}
