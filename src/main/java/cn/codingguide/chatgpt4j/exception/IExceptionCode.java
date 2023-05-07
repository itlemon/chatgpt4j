package cn.codingguide.chatgpt4j.exception;

/**
 * @author itlemon
 * Created on 2023-04-04
 */
public interface IExceptionCode {

    /**
     * 错误码
     *
     * @return 错误码
     */
    int code();

    /**
     * 错误消息
     *
     * @return 错误消息
     */
    String msg();

}
