package cn.codingguide.chatgpt4j.domain.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author itlemon
 * Created on 2023-04-29
 */
public class CommonListResponse<T> implements Serializable {

    /**
     * 数据模型，例如list
     */
    private String object;

    /**
     * 具体数据列表
     */
    private List<T> data;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonListResponse{" +
                "object='" + object + '\'' +
                ", data=" + data +
                '}';
    }
}
