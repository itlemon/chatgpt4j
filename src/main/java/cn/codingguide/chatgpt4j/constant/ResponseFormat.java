package cn.codingguide.chatgpt4j.constant;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-19
 */
public enum ResponseFormat {

    /**
     * 格式
     */
    URL("url"),
    B64_JSON("b64_json");

    private final String format;

    ResponseFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
