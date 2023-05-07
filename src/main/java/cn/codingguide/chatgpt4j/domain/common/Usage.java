package cn.codingguide.chatgpt4j.domain.common;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * @author itlemon
 * Created on 2023-04-18
 */
public class Usage implements Serializable {

    @SerializedName("prompt_tokens")
    private long promptTokens;

    @SerializedName("completion_tokens")
    private long completionTokens;

    @SerializedName("total_tokens")
    private long totalTokens;

    public long getPromptTokens() {
        return promptTokens;
    }

    public void setPromptTokens(long promptTokens) {
        this.promptTokens = promptTokens;
    }

    public long getCompletionTokens() {
        return completionTokens;
    }

    public void setCompletionTokens(long completionTokens) {
        this.completionTokens = completionTokens;
    }

    public long getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(long totalTokens) {
        this.totalTokens = totalTokens;
    }

    @Override
    public String toString() {
        return "Usage{" +
                "promptTokens=" + promptTokens +
                ", completionTokens=" + completionTokens +
                ", totalTokens=" + totalTokens +
                '}';
    }
}
