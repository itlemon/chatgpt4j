package cn.codingguide.chatgpt4j.domain.moderations;

import cn.codingguide.chatgpt4j.constant.ModelSelector;
import cn.codingguide.chatgpt4j.constant.ModerationModel;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * @author itlemon
 * Created on 2023-04-28
 */
public class ModerationRequest implements Serializable {

    private final transient Builder builder;

    private final List<String> input;
    private final String model;

    private ModerationRequest(Builder builder) {
        this.builder = builder;
        this.input = builder.input;
        this.model = builder.model.getModel();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder;
    }

    public List<String> getInput() {
        return input;
    }

    public String getModel() {
        return model;
    }

    public static final class Builder {

        /**
         * 必需参数：需要分类审核的文本
         */
        @NotNull
        private final List<String> input = Lists.newArrayList();

        /**
         * 非必需参数：目前只有两个可用模型：text-moderation-stable 和 text-moderation-latest.
         * 后者会自动更新到最新的模型，推荐使用
         */
        private ModelSelector model = ModerationModel.TEXT_MODERATION_LATEST;

        private Builder() {
        }

        public Builder addInput(String input) {
            this.input.add(input);
            return this;
        }

        public Builder addAllInput(@NotNull List<String> input) {
            this.input.addAll(input);
            return this;
        }

        public Builder model(ModelSelector model) {
            this.model = model;
            return this;
        }

        public ModerationRequest build() {
            return new ModerationRequest(this);
        }

    }

    @Override
    public String toString() {
        return "ModerationRequest{" +
                "input=" + input +
                ", model='" + model + '\'' +
                '}';
    }
}
