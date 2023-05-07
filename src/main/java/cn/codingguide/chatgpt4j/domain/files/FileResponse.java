package cn.codingguide.chatgpt4j.domain.files;

import java.io.Serializable;
import java.util.List;

/**
 * @author itlemon
 * Created on 2023-04-24
 */
public class FileResponse implements Serializable {

    /**
     * 指示对象类型（例如，"list"）的字符串
     */
    private String object;

    /**
     * 文件信息列表
     */
    private List<FileItem> data;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<FileItem> getData() {
        return data;
    }

    public void setData(List<FileItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FileResponse{" +
                "object='" + object + '\'' +
                ", data=" + data +
                '}';
    }
}
