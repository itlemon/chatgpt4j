package cn.codingguide.chatgpt4j.constant;

/**
 * @author itlemon
 * Created on 2023-04-19
 */
public enum ImageSize {

    /**
     * 图片尺寸
     */
    SIZE_1024("1024x1024"),
    SIZE_512("512x512"),
    SIZE_256("256x256");

    private final String size;

    ImageSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
