package cn.codingguide.chatgpt4j.domain.finetune;

import java.io.Serializable;
import java.util.List;

/**
 * @author itlemon
 * Created on 2023-04-28
 */
public class FineTuneEventListResponse implements Serializable {

    private String object;

    private List<FineTuneEvent> data;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<FineTuneEvent> getData() {
        return data;
    }

    public void setData(List<FineTuneEvent> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FineTuneEventListResponse{" +
                "object='" + object + '\'' +
                ", data=" + data +
                '}';
    }
}
