package cn.codingguide.chatgpt4j.domain.moderations;

import java.io.Serializable;
import java.util.List;

/**
 * @author itlemon
 * Created on 2023-04-28
 */
public class ModerationResponse implements Serializable {

    private String id;
    private String model;
    private List<ModerationResult> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ModerationResult> getResults() {
        return results;
    }

    public void setResults(List<ModerationResult> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ModerationResponse{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", results=" + results +
                '}';
    }
}
