package cn.codingguide.chatgpt4j.exception;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-04
 */
public enum ChatGptExceptionMsg implements IExceptionMsg {

    /**
     * 内部预定错误类型
     */
    INVALID_PARAM_ERROR(400, "Invalid Parameter."),
    API_KEY_LIST_NOT_EMPTY(500, "Api key list must be not empty."),
    COMMON_SYSTEM_ERROR(500, "Common system error."),

    /**
     * Open AI相关的错误，参考链接：<a href="https://platform.openai.com/docs/guides/error-codes/api-errors">链接</a>
     */
    OPEN_AI_AUTHENTICATION_ERROR(401,
            "Invalid Authentication | The requesting API key is not correct | Your account is not part of an organization"),
    OPEN_AI_NOT_FOUND_ERROR(404, "The resource not found"),
    OPEN_AI_RESOURCE_LIMIT_ERROR(429,
            "You are sending requests too quickly | You exceeded your current quota | Our servers are experiencing high traffic"),
    OPENAI_SERVER_ERROR(500, "The server (openai.com) had an error while processing your request");


    private final int code;

    private final String msg;

    ChatGptExceptionMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
