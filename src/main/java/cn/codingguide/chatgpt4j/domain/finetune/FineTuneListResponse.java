package cn.codingguide.chatgpt4j.domain.finetune;

import java.io.Serializable;
import java.util.List;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-28
 */
public class FineTuneListResponse implements Serializable {

    private String object;

    private List<FineTuneResponse> data;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<FineTuneResponse> getData() {
        return data;
    }

    public void setData(List<FineTuneResponse> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FineTuneListResponse{" +
                "object='" + object + '\'' +
                ", data=" + data +
                '}';
    }
}
