package cn.codingguide.chatgpt4j.domain.images;

import java.io.Serializable;
import java.util.List;

/**
 * @author itlemon
 * Created on 2023-04-19
 */
public class ImageResponse implements Serializable {

    /**
     * 响应创建时间戳
     */
    private long created;

    /**
     * 图片列表
     */
    private List<ImageItem> data;

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public List<ImageItem> getData() {
        return data;
    }

    public void setData(List<ImageItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ImageResponse{" +
                "created=" + created +
                ", data=" + data +
                '}';
    }
}
