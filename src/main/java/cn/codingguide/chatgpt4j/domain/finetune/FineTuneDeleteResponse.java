package cn.codingguide.chatgpt4j.domain.finetune;

import java.io.Serializable;

/**
 * @author itlemon
 * Created on 2023-04-28
 */
public class FineTuneDeleteResponse implements Serializable {

    private String id;

    private String object;

    private boolean deleted;

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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "FineTuneDeleteResponse{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
