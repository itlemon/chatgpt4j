package cn.codingguide.chatgpt4j.domain.chat;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * @author itlemon
 * Created on 2023-04-18
 */
public class ChatChoice implements Serializable {

    private long index;

    /**
     * 请求参数stream为false返回是message
     */
    private Message message;

    /**
     * 响应完成的原因，可能是"length"（达到max_tokens）、"stop"（找到stop标记）、"content_filter"（内容过滤）或 null（API尚未处理完成）
     */
    @SerializedName("finish_reason")
    private String finishReason;

    /**
     * 请求参数stream为true返回是delta
     */
    private Message delta;

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    public Message getDelta() {
        return delta;
    }

    public void setDelta(Message delta) {
        this.delta = delta;
    }

    @Override
    public String toString() {
        return "ChatChoice{" +
                "index=" + index +
                ", message=" + message +
                ", finishReason='" + finishReason + '\'' +
                ", delta=" + delta +
                '}';
    }
}
