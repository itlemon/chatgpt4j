package cn.codingguide.chatgpt4j.exception;

/**
 * 错误码及说明
 *
 * @author itlemon
 * Created on 2023-04-04
 */
public enum ChatGptExceptionCode implements IExceptionCode {

    /**
     * 通用SDK错误
     */
    SDK_COMMON_ERROR(504, "Common gateway error."),

    /**
     * Open AI相关的错误，参考链接：<a href="https://platform.openai.com/docs/guides/error-codes/api-errors">链接</a>
     */
    OPEN_AI_INVALID_REQUEST_ERROR(400,
            "An InvalidRequestError indicates that your request was malformed or missing some required parameters, "
                    + "such as a token or an input. This could be due to a typo, a formatting error, or a logic error"
                    + " in your code."),
    OPEN_AI_AUTHENTICATION_ERROR(401,
            "Invalid Authentication | The requesting API key is not correct | Your account is not part of an "
                    + "organization"),
    OPEN_AI_NOT_FOUND_ERROR(404, "The resource not found"),
    OPEN_AI_RESOURCE_LIMIT_ERROR(429,
            "You are sending requests too quickly | You exceeded your current quota | Our servers are experiencing "
                    + "high traffic"),
    OPENAI_SERVER_ERROR(500, "The server (openai.com) had an error while processing your request");


    private final int code;

    private final String msg;

    ChatGptExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ChatGptExceptionCode parseFromCode(int code) {
        // 优先返回正确的错误码，最后返回通用错误码
        if (code == OPEN_AI_INVALID_REQUEST_ERROR.code) {
            return OPEN_AI_INVALID_REQUEST_ERROR;
        } else if (code == OPEN_AI_AUTHENTICATION_ERROR.code) {
            return OPEN_AI_AUTHENTICATION_ERROR;
        } else if (code == OPEN_AI_NOT_FOUND_ERROR.code) {
            return OPEN_AI_NOT_FOUND_ERROR;
        } else if (code == OPEN_AI_RESOURCE_LIMIT_ERROR.code) {
            return OPEN_AI_RESOURCE_LIMIT_ERROR;
        } else if (code == OPENAI_SERVER_ERROR.code) {
            return OPENAI_SERVER_ERROR;
        } else {
            return SDK_COMMON_ERROR;
        }
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
