package cn.codingguide.chatgpt4j.domain.audio;

import java.io.Serializable;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-23
 */
public class TranscriptionResponse implements Serializable {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TranscriptionResponse{" +
                "text='" + text + '\'' +
                '}';
    }
}
