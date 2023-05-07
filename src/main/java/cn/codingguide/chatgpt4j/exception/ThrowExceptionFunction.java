package cn.codingguide.chatgpt4j.exception;

/**
 * @author itlemon
 * Created on 2023-04-23
 */
@FunctionalInterface
public interface ThrowExceptionFunction {

    /**
     * 抛出指定异常消息的异常信息
     *
     * @param code 错误码
     * @param message 异常消息
     */
    void throwMessage(ChatGptExceptionCode code, String message);

}
