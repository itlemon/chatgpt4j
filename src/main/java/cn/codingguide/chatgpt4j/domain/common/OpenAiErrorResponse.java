package cn.codingguide.chatgpt4j.domain.common;

import java.io.Serializable;

/**
 * 调用Open AI接口发生错误后的返回体
 *
 * @author itlemon
 * Created on 2023-04-05
 */
public class OpenAiErrorResponse implements Serializable {

    private OpenAiError error;

    public OpenAiError getError() {
        return error;
    }

    public void setError(OpenAiError error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "OpenAiErrorResponse{" +
                "error=" + error +
                '}';
    }
}
