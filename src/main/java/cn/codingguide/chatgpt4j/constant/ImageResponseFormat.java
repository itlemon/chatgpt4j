package cn.codingguide.chatgpt4j.constant;

/**
 * @author itlemon
 * Created on 2023-04-19
 */
public enum ImageResponseFormat {

    /**
     * 格式
     */
    URL("url"),
    B64_JSON("b64_json");

    private final String format;

    ImageResponseFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
