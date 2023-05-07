package cn.codingguide.chatgpt4j.domain.chat;

import java.io.Serializable;
import java.util.Arrays;

import cn.codingguide.chatgpt4j.domain.common.Usage;

/**
 * @author itlemon
 * Created on 2023-04-18
 */
public class ChatCompletionResponse implements Serializable {

    /**
     * 此响应的唯一ID
     */
    private String id;

    /**
     * 指示对象类型（例如，"chat.completion"）的字符串
     */
    private String object;

    /**
     * 响应创建时间戳
     */
    private long created;

    /**
     * 用于生成响应的模型的名称或ID
     */
    private String model;

    /**
     * API使用情况的信息
     */
    private Usage usage;

    /**
     * 一个数组，表示生成的文本选项
     */
    private ChatChoice[] choices;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public ChatChoice[] getChoices() {
        return choices;
    }

    public void setChoices(ChatChoice[] choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "ChatCompletionResponse{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", model='" + model + '\'' +
                ", usage=" + usage +
                ", choices=" + Arrays.toString(choices) +
                '}';
    }
}
