package cn.codingguide.chatgpt4j.domain.images;

import java.io.Serializable;

import javax.annotation.Nonnull;

import cn.codingguide.chatgpt4j.constant.ImageResponseFormat;
import com.google.gson.annotations.SerializedName;

import cn.codingguide.chatgpt4j.constant.ImageSize;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-19
 */
public class ImageVariation implements Serializable {

    private final transient Builder builder;

    private final String image;

    private final Integer n;

    private final String size;

    @SerializedName("response_format")
    private final String responseFormat;

    private final String user;

    private ImageVariation(Builder builder) {
        this.builder = builder;
        this.image = builder.image;
        this.n = builder.countOfCompletion4EachPrompt;
        this.size = builder.size.getSize();
        this.responseFormat = builder.responseFormat.getFormat();
        this.user = builder.user;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder;
    }

    public String getImage() {
        return image;
    }

    public Integer getN() {
        return n;
    }

    public String getSize() {
        return size;
    }

    public String getResponseFormat() {
        return responseFormat;
    }

    public String getUser() {
        return user;
    }

    public static final class Builder {

        /**
         * 必需参数：要编辑的图像。 必须是有效的 PNG 文件，小于 4MB，并且是方形的。 如果未提供遮罩，图像必须具有透明度，将用作遮罩。
         */
        @Nonnull
        private String image;

        /**
         * 非必需参数：生成结果的数量。默认为1，最大值为10。为了可读性，该参数名称语义化了一下，原参数名称是n，详情请参考：
         * <a href="https://platform.openai.com/docs/api-reference/images/create-edit#images/create-edit-n">链接</a>
         */
        private Integer countOfCompletion4EachPrompt = 1;

        /**
         * 非必需参数：生成图片的尺寸，分别有：256x256  512x512  1024x1024，默认是1024x1024
         */
        private ImageSize size = ImageSize.SIZE_1024;

        /**
         * 非必需参数，告知openai需要使用何种方式返回图片的内容，有两个选项，url和b64_json，默认是url
         */
        private ImageResponseFormat responseFormat = ImageResponseFormat.URL;

        /**
         * 非必需参数：指定对话或用户的标识符。如果提供了此参数，则API将尝试利用先前与相同标识符使用的上下文进行生成。否则，它将使用新的上下文。
         */
        private String user;

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Builder countOfCompletion4EachPrompt(int countOfCompletion4EachPrompt) {
            this.countOfCompletion4EachPrompt = countOfCompletion4EachPrompt;
            return this;
        }

        public Builder size(ImageSize size) {
            this.size = size;
            return this;
        }

        public Builder responseFormat(ImageResponseFormat responseFormat) {
            this.responseFormat = responseFormat;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public ImageVariation build() {
            return new ImageVariation(this);
        }

    }

    @Override
    public String toString() {
        return "ImageVariation{" +
                "image='" + image + '\'' +
                ", n=" + n +
                ", size='" + size + '\'' +
                ", responseFormat='" + responseFormat + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
