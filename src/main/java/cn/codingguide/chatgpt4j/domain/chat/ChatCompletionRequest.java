package cn.codingguide.chatgpt4j.domain.chat;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import cn.codingguide.chatgpt4j.constant.ChatCompletionsModel;
import cn.codingguide.chatgpt4j.constant.ModelSelector;

/**
 * @author itlemon
 * Created on 2023-04-18
 */
public class ChatCompletionRequest implements Serializable {

    /**
     * 该字段不能被序列化为json，这个是保留字段，用于Builder模式
     */
    private final transient Builder builder;

    private final String model;

    private final List<Message> messages;

    private final Double temperature;

    @SerializedName("top_p")
    private final Double topP;

    private final Integer n;

    private final boolean stream;

    private final List<String> stop;

    @SerializedName("max_tokens")
    private final Integer maxTokens;

    @SerializedName("presence_penalty")
    private final double presencePenalty;

    @SerializedName("frequency_penalty")
    private final double frequencyPenalty;

    @SerializedName("logit_bias")
    private final Map<String, Integer> logitBias;

    private final String user;

    private ChatCompletionRequest(Builder builder) {
        this.builder = builder;
        this.model = builder.model.getModel();
        this.messages = builder.messages;
        this.temperature = builder.temperature;
        this.topP = builder.topP;
        this.n = builder.countOfCompletion4EachPrompt;
        this.stream = builder.stream;
        this.stop = builder.stop;
        this.maxTokens = builder.maxTokens;
        this.presencePenalty = builder.presencePenalty;
        this.frequencyPenalty = builder.frequencyPenalty;
        this.logitBias = builder.logitBias;
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

    public List<Message> getMessages() {
        return messages;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getTopP() {
        return topP;
    }

    public Integer getN() {
        return n;
    }

    public boolean isStream() {
        return stream;
    }

    public List<String> getStop() {
        return stop;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public double getPresencePenalty() {
        return presencePenalty;
    }

    public double getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public Map<String, Integer> getLogitBias() {
        return logitBias;
    }

    public String getUser() {
        return user;
    }

    public static final class Builder {

        /**
         * 必需参数：选择的模型，当前稳定版本的模型：<a href="https://platform.openai.com/docs/models/gpt-3-5">链接</a>
         */
        @NotNull
        private ModelSelector model = ChatCompletionsModel.GPT_3_5_TURBO;

        /**
         * 详细描述，携带上下文信息
         */
        @NotNull
        private final List<Message> messages = Lists.newArrayList();

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

        /**
         * 非必需参数：生成结果的数量。默认为1。为了可读性，该参数名称语义化了一下，原参数名称是n，详情请参考：
         * <a href="https://platform.openai.com/docs/api-reference/chat/create#chat/create-n">链接</a>
         */
        private Integer countOfCompletion4EachPrompt = 1;

        /**
         * 非必需参数：返回多个json格式的响应结果对象，每个响应对象表示一个生成的文本段落。默认为false，表示返回单个响应对象。
         */
        private boolean stream;

        /**
         * 非必须参数：一组标记，即在生成结果中找到这些标记时停止生成。默认为null，表示不停止生成。
         */
        private List<String> stop;

        /**
         * 非必需参数：The total length of input tokens and generated tokens is limited by the model's context length.
         * 生成的文本长度取决于模型，这里默认值为2048，最大支持4096
         */
        private Integer maxTokens = 2048;

        /**
         * 非必需参数：控制模型是否考虑已经存在的令牌。默认值为0，较高的值会增加罚款并降低已经出现在生成结果中的令牌的权重。
         */
        private double presencePenalty = 0;

        /**
         * 非必须参数：控制模型是否依靠频繁出现的令牌。默认值为0，较高的值会增加罚款并降低诸如常见单词之类的常见令牌的权重。
         */
        private double frequencyPenalty = 0;

        /**
         * 非必需参数：控制模型生成的文本中令牌的偏好。正值会提高相应的令牌权重，负值则会降低相应的权重。默认为null，表示不使用偏差。
         */
        private Map<String, Integer> logitBias;

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

        public Builder addAllMessages(List<Message> messages) {
            this.messages.addAll(messages);
            return this;
        }

        public Builder addMessage(Message message) {
            this.messages.add(message);
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

        public Builder countOfCompletion4EachPrompt(int countOfCompletion4EachPrompt) {
            this.countOfCompletion4EachPrompt = countOfCompletion4EachPrompt;
            return this;
        }

        public Builder stream(boolean stream) {
            this.stream = stream;
            return this;
        }

        public Builder stop(List<String> stop) {
            this.stop = stop;
            return this;
        }

        public Builder maxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
            return this;
        }

        public Builder presencePenalty(double presencePenalty) {
            this.presencePenalty = presencePenalty;
            return this;
        }

        public Builder frequencyPenalty(double frequencyPenalty) {
            this.frequencyPenalty = frequencyPenalty;
            return this;
        }

        public Builder logitBias(Map<String, Integer> logitBias) {
            this.logitBias = logitBias;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public ChatCompletionRequest build() {
            return new ChatCompletionRequest(this);
        }

    }

    @Override
    public String toString() {
        return "ChatCompletionRequest{" +
                "model='" + model + '\'' +
                ", messages=" + messages +
                ", temperature=" + temperature +
                ", topP=" + topP +
                ", n=" + n +
                ", stream=" + stream +
                ", stop=" + stop +
                ", maxTokens=" + maxTokens +
                ", presencePenalty=" + presencePenalty +
                ", frequencyPenalty=" + frequencyPenalty +
                ", logitBias=" + logitBias +
                ", user='" + user + '\'' +
                '}';
    }
}
