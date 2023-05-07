package cn.codingguide.chatgpt4j.domain.finetune;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author itlemon
 * Created on 2023-04-28
 */
public class FineTuneEvent implements Serializable {

    /**
     * 对象标识，例如：fine-tune-event
     */
    private String object;

    /**
     * 事件创建时间戳
     */
    @SerializedName("created_at")
    private long createdAt;

    /**
     * 级别
     */
    private String level;

    /**
     * 消息
     */
    private String message;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FineTuneEvent{" +
                "object='" + object + '\'' +
                ", createdAt=" + createdAt +
                ", level='" + level + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
