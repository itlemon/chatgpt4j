package cn.codingguide.chatgpt4j.domain.common;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * @author itlemon
 * Created on 2023-04-18
 */
public class Choice implements Serializable {

    /**
     * 生成的文本
     */
    private String text;

    /**
     * 与提示中对应的下标
     */
    private long index;

    /**
     * 生成每个令牌时的对数概率的数组。仅在请求中设置了logprobs参数时才会返回。
     */
    @SerializedName("logprobs")
    private Object logProbs;

    /**
     * 响应完成的原因，可能是"length"（达到max_tokens）、"stop"（找到stop标记）或"eof"（到达文档结尾）
     */
    @SerializedName("finish_reason")
    private String finishReason;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public Object getLogProbs() {
        return logProbs;
    }

    public void setLogProbs(Object logProbs) {
        this.logProbs = logProbs;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "text='" + text + '\'' +
                ", index=" + index +
                ", logProbs=" + logProbs +
                ", finishReason='" + finishReason + '\'' +
                '}';
    }
}
