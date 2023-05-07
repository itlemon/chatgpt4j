package cn.codingguide.chatgpt4j.domain.embeddings;

import java.io.Serializable;
import java.util.List;

import cn.codingguide.chatgpt4j.domain.common.Usage;

/**
 * @author itlemon
 * Created on 2023-04-20
 */
public class EmbeddingResponse implements Serializable {

    /**
     * 指示对象类型（例如，"list"）的字符串
     */
    private String object;

    /**
     * 实体列表
     */
    private List<EmbeddingItem> data;

    /**
     * 模型
     */
    private String model;

    /**
     * API使用情况
     */
    private Usage usage;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<EmbeddingItem> getData() {
        return data;
    }

    public void setData(List<EmbeddingItem> data) {
        this.data = data;
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

    @Override
    public String toString() {
        return "EmbeddingResponse{" +
                "object='" + object + '\'' +
                ", data=" + data +
                ", model='" + model + '\'' +
                ", usage=" + usage +
                '}';
    }
}
