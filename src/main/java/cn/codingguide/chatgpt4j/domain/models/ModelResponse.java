package cn.codingguide.chatgpt4j.domain.models;

import java.io.Serializable;
import java.util.List;

/**
 * ChatGPT模型返回体
 *
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-04
 */
public class ModelResponse implements Serializable {

    private String object;

    private List<Model> data;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Model> getData() {
        return data;
    }

    public void setData(List<Model> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ModelResponse{" +
                "object='" + object + '\'' +
                ", data=" + data +
                '}';
    }
}
