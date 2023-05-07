package cn.codingguide.chatgpt4j.domain.embeddings;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import cn.codingguide.chatgpt4j.constant.EmbeddingsModel;
import cn.codingguide.chatgpt4j.constant.ModelSelector;

/**
 * @author itlemon
 * Created on 2023-04-20
 */
public class EmbeddingRequest implements Serializable {

    private final transient Builder builder;

    private final String model;

    private final List<String> input;

    private final String user;

    private EmbeddingRequest(Builder builder) {
        this.builder = builder;
        this.model = builder.model.getModel();
        this.input = builder.input;
        this.user = builder.user;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder;
    }

    public String getModel() {
        return model;
    }

    public List<String> getInput() {
        return input;
    }

    public String getUser() {
        return user;
    }

    public static final class Builder {

        /**
         * 必需参数：选择的模型，当前稳定版本的模型：<a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">链接</a>
         */
        @NotNull
        private ModelSelector model = EmbeddingsModel.TEXT_EMBEDDING_ADA_002;

        /**
         * 必需参数：Input text to get embeddings for, encoded as a string or array of tokens. To get embeddings for multiple
         * inputs in a single request, pass an array of strings or array of token arrays. Each input must not exceed
         * 8192 tokens in length.
         */
        @NotNull
        private final List<String> input = Lists.newArrayList();

        /**
         * 非必需参数：指定对话或用户的标识符。如果提供了此参数，则API将尝试利用先前与相同标识符使用的上下文进行生成。否则，它将使用新的上下文。
         */
        private String user;

        private Builder() {
        }

        public Builder model(ModelSelector model) {
            this.model = model;
            return this;
        }

        public Builder addInput(String input) {
            this.input.add(input);
            return this;
        }

        public Builder addAllInput(List<String> input) {
            this.input.addAll(input);
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public EmbeddingRequest build() {
            return new EmbeddingRequest(this);
        }

    }

    @Override
    public String toString() {
        return "EmbeddingRequest{" +
                "model='" + model + '\'' +
                ", input=" + input +
                ", user='" + user + '\'' +
                '}';
    }
}
