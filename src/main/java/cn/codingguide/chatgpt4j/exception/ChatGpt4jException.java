package cn.codingguide.chatgpt4j.exception;

import cn.hutool.core.util.StrUtil;

/**
 * @author itlemon
 * Created on 2023-04-04
 */
public class ChatGpt4jException extends RuntimeException {

    private final int code;
    private final String msg;

    public ChatGpt4jException(ChatGptExceptionCode code, String msg) {
        super(msg);
        this.code = code.code();
        this.msg = StrUtil.isBlank(msg) ? code.msg() : msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
