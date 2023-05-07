package cn.codingguide.chatgpt4j.domain.edits;

import java.io.Serializable;

import org.jetbrains.annotations.NotNull;

import com.google.gson.annotations.SerializedName;

import cn.codingguide.chatgpt4j.constant.EditModel;
import cn.codingguide.chatgpt4j.constant.ModelSelector;

/**
 * @author itlemon
 * Created on 2023-04-19
 */
public class EditRequest implements Serializable {

    private final transient Builder builder;

    private final String model;

    private final String input;

    private final String instruction;

    private final Integer n;

    private final Double temperature;

    @SerializedName("top_p")
    private final Double topP;

    private EditRequest(Builder builder) {
        this.builder = builder;
        this.model = builder.model.getModel();
        this.input = builder.input;
        this.instruction = builder.instruction;
        this.n = builder.countOfCompletion4EachPrompt;
        this.temperature = builder.temperature;
        this.topP = builder.topP;
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

    public String getInput() {
        return input;
    }

    public String getInstruction() {
        return instruction;
    }

    public Integer getN() {
        return n;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getTopP() {
        return topP;
    }

    public static final class Builder {

        /**
         * 必需参数：ID of the model to use. You can use the text-davinci-edit-001 or code-davinci-edit-001 model with
         * this endpoint.
         */
        @NotNull
        private ModelSelector model = EditModel.TEXT_DAVINCI_EDIT_001;

        /**
         * 非必需参数：需要被编辑的文本
         */
        private String input = "";

        /**
         * 必需参数：告诉模型，你希望他对input做什么操作
         */
        @NotNull
        private String instruction = "";

        /**
         * 非必需参数：生成结果的数量。默认为1。为了可读性，该参数名称语义化了一下，原参数名称是n，详情请参考：
         * <a href="https://platform.openai.com/docs/api-reference/edits/create#edits/create-n">链接</a>
         */
        private Integer countOfCompletion4EachPrompt = 1;

        /**
         * 非必需参数：生成结果的多样性程度，使用什么采样温度，介于 0 和 2 之间。较高的值（如 0.8）将使输出更加随机，而较低的值（如 0.2）将使输出更加集中和确定。
         * 我们通常建议更改此设置，但不要同时更改 top_p
         */
        private Double temperature = 0D;

        /**
         * 非必需参数：一种替代温度采样的方法，称为核采样，其中模型考虑具有 top_p 概率质量的标记的结果。 所以 0.1 意味着只考虑构成前 10% 概率质量的标记。
         * 我们通常建议更改此设置，但不要同时更改 temperature
         */
        private Double topP = 1D;

        private Builder() {
        }

        public Builder model(ModelSelector model) {
            this.model = model;
            return this;
        }

        public Builder input(String input) {
            this.input = input;
            return this;
        }

        public Builder instruction(String instruction) {
            this.instruction = instruction;
            return this;
        }

        public Builder countOfCompletion4EachPrompt(int countOfCompletion4EachPrompt) {
            this.countOfCompletion4EachPrompt = countOfCompletion4EachPrompt;
            return this;
        }

        public Builder temperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder topP(double topP) {
            this.topP = topP;
            return this;
        }

        public EditRequest build() {
            return new EditRequest(this);
        }

    }

    @Override
    public String toString() {
        return "EditRequest{" +
                "model='" + model + '\'' +
                ", input='" + input + '\'' +
                ", instruction='" + instruction + '\'' +
                ", n=" + n +
                ", temperature=" + temperature +
                ", topP=" + topP +
                '}';
    }
}
