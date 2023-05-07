package cn.codingguide.chatgpt4j.domain.embeddings;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author itlemon
 * Created on 2023-04-20
 */
public class EmbeddingItem implements Serializable {

    private String object;
    private List<BigDecimal> embedding;
    private Integer index;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<BigDecimal> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<BigDecimal> embedding) {
        this.embedding = embedding;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "EmbeddingItem{" +
                "object='" + object + '\'' +
                ", embedding=" + embedding +
                ", index=" + index +
                '}';
    }
}
