package cn.codingguide.chatgpt4j.domain.images;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * @author itlemon
 * Created on 2023-04-19
 */
public class ImageItem implements Serializable {

    private String url;

    @SerializedName("b64_json")
    private String b64Json;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getB64Json() {
        return b64Json;
    }

    public void setB64Json(String b64Json) {
        this.b64Json = b64Json;
    }

    @Override
    public String toString() {
        return "ImageItem{" +
                "url='" + url + '\'' +
                ", b64Json='" + b64Json + '\'' +
                '}';
    }
}
