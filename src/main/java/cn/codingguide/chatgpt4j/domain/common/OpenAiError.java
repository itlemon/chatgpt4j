package cn.codingguide.chatgpt4j.domain.common;

import java.io.Serializable;

/**
 * @author itlemon
 * Created on 2023-04-05
 */
public class OpenAiError implements Serializable {

    private String code;
    private String message;
    private String type;
    private String param;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "OpenAiError{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                '}';
    }

}
