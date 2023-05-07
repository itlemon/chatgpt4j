package cn.codingguide.chatgpt4j.domain.audio;

import java.io.Serializable;

/**
 * @author itlemon
 * Created on 2023-04-23
 */
public class TranslationResponse implements Serializable {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TranslationResponse{" +
                "text='" + text + '\'' +
                '}';
    }
}
