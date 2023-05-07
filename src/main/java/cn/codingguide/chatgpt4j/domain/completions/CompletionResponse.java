package cn.codingguide.chatgpt4j.domain.completions;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

import cn.codingguide.chatgpt4j.domain.common.Choice;
import cn.codingguide.chatgpt4j.domain.common.Usage;

/**
 * @author itlemon
 * Created on 2023-04-18
 */
public class CompletionResponse {

    /**
     * 此响应的唯一ID
     */
    private String id;

    /**
     * 指示对象类型（例如，"text_completion"）的字符串
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
     * 一个数组，表示生成的文本选项
     */
    private Choice[] choices;

    /**
     * API使用情况的信息
     */
    private Usage usage;

    /**
     * 响应停止的原因，如果存在的话。可能是null、"timeout"（超时）或"completions_requested"（达到请求的完成数）
     */
    @SerializedName("stop_reason")
    private String stopReason;

    /**
     * 生成响应的完整模型输出。仅在请求中设置了stream参数时才会返回
     */
    @SerializedName("model_output")
    private String modelOutput;

    /**
     * 如果请求中设置了stream参数，则为流ID
     */
    @SerializedName("stream_id")
    private String streamId;

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

    public Choice[] getChoices() {
        return choices;
    }

    public void setChoices(Choice[] choices) {
        this.choices = choices;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason;
    }

    public String getModelOutput() {
        return modelOutput;
    }

    public void setModelOutput(String modelOutput) {
        this.modelOutput = modelOutput;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    @Override
    public String toString() {
        return "CompletionResponse{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", model='" + model + '\'' +
                ", choices=" + Arrays.toString(choices) +
                ", usage=" + usage +
                ", stopReason='" + stopReason + '\'' +
                ", modelOutput='" + modelOutput + '\'' +
                ", streamId='" + streamId + '\'' +
                '}';
    }
}
