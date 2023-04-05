package cn.codingguide.chatgpt4j.exception;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-04
 */
public class ChatGpt4jException extends RuntimeException {

    private final int code;
    private final String msg;

    public ChatGpt4jException(IExceptionMsg exceptionMsg) {
        super(exceptionMsg.msg());
        this.code = exceptionMsg.code();
        this.msg = exceptionMsg.msg();
    }

    public ChatGpt4jException(String msg) {
        super(msg);
        this.code = ChatGptExceptionMsg.COMMON_SYSTEM_ERROR.code();
        this.msg = msg;
    }

    public ChatGpt4jException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ChatGpt4jException() {
        super(ChatGptExceptionMsg.COMMON_SYSTEM_ERROR.msg());
        this.code = ChatGptExceptionMsg.COMMON_SYSTEM_ERROR.code();
        this.msg = ChatGptExceptionMsg.COMMON_SYSTEM_ERROR.msg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
