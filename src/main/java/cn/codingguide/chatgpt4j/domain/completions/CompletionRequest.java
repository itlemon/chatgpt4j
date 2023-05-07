package cn.codingguide.chatgpt4j.domain.completions;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.google.gson.annotations.SerializedName;

import cn.codingguide.chatgpt4j.constant.CompletionsModel;
import cn.codingguide.chatgpt4j.constant.ModelSelector;

/**
 * @author itlemon
 * Created on 2023-04-05
 */
public class CompletionRequest implements Serializable {

    /**
     * 该字段不能被序列化为json，这个是保留字段，用于Builder模式
     */
    private final transient Builder builder;

    private final String model;

    private final String prompt;

    private final String suffix;

    @SerializedName("max_tokens")
    private final Integer maxTokens;

    private final Double temperature;

    @SerializedName("top_p")
    private final Double topP;

    private final Integer n;

    private final boolean stream;

    @SerializedName("logprobs")
    private final Integer logProbs;

    private final boolean echo;

    private final List<String> stop;

    @SerializedName("presence_penalty")
    private final double presencePenalty;

    @SerializedName("frequency_penalty")
    private final double frequencyPenalty;

    @SerializedName("best_of")
    private final int bestOf;

    @SerializedName("logit_bias")
    private final Map<String, Integer> logitBias;

    private final String user;

    private CompletionRequest(Builder builder) {
        this.builder = builder;
        this.model = builder.model.getModel();
        this.prompt = builder.prompt;
        this.suffix = builder.suffix;
        this.maxTokens = builder.maxTokens;
        this.temperature = builder.temperature;
        this.topP = builder.topP;
        this.n = builder.countOfCompletion4EachPrompt;
        this.stream = builder.stream;
        this.logProbs = builder.logProbs;
        this.echo = builder.echo;
        this.stop = builder.stop;
        this.presencePenalty = builder.presencePenalty;
        this.frequencyPenalty = builder.frequencyPenalty;
        this.bestOf = builder.bestOf;
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

    public String getPrompt() {
        return prompt;
    }

    public String getSuffix() {
        return suffix;
    }

    public Integer getMaxTokens() {
        return maxTokens;
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

    public Integer getLogProbs() {
        return logProbs;
    }

    public boolean isEcho() {
        return echo;
    }

    public List<String> getStop() {
        return stop;
    }

    public double getPresencePenalty() {
        return presencePenalty;
    }

    public double getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public int getBestOf() {
        return bestOf;
    }

    public Map<String, Integer> getLogitBias() {
        return logitBias;
    }

    public String getUser() {
        return user;
    }

    public static final class Builder {

        /**
         * 必需参数：当前稳定的Completions接口适配的模型列表，参考链接：
         * <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">链接</a>
         */
        @NotNull
        private ModelSelector model = CompletionsModel.TEXT_DAVINCI_003;

        /**
         * 必需参数：要生成自动补全建议的文本片段
         */
        @NotNull
        private String prompt = "Say this is a test";

        /**
         * 非必需参数：格式化输出的后缀，一般使用\n
         */
        private String suffix;

        /**
         * 非必需参数：生成文本中最多包含的令牌数。默认值为16，最大值为2048，过小的值可能会导致结果不完整。目前最大支持4096
         */
        private Integer maxTokens = 2048;

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
         * 非必需参数：生成结果的数量。默认为1，最大值为4。为了可读性，该参数名称语义化了一下，原参数名称是n，详情请参考：
         * <a href="https://platform.openai.com/docs/api-reference/completions/create#completions/create-n">链接</a>
         */
        private Integer countOfCompletion4EachPrompt = 1;

        /**
         * 非必需参数：返回多个json格式的响应结果对象，每个响应对象表示一个生成的文本段落。默认为false，表示返回单个响应对象。
         */
        private boolean stream;

        /**
         * 非必需参数：指示模型是否返回生成每个令牌时的对数概率，以及在完成时返回的标记列表和相应的对数概率。默认为null，表示不返回对数概率。
         */
        private Integer logProbs;

        /**
         * 非必需参数：指示API是否将提示包含在响应中。默认为true，表示将提示包含在响应中。
         */
        private boolean echo;

        /**
         * 非必须参数：一组标记，即在生成结果中找到这些标记时停止生成。默认为null，表示不停止生成。
         */
        private List<String> stop;

        /**
         * 非必需参数：控制模型是否考虑已经存在的令牌。默认值为0，较高的值会增加罚款并降低已经出现在生成结果中的令牌的权重。
         */
        private double presencePenalty = 0;

        /**
         * 非必须参数：控制模型是否依靠频繁出现的令牌。默认值为0，较高的值会增加罚款并降低诸如常见单词之类的常见令牌的权重。
         */
        private double frequencyPenalty = 0;

        /**
         * 非必需参数：在多个生成结果之间进行选择的数量。默认为1，表示只返回单个最佳结果。较高的值将导致更多的计算和响应时间。
         */
        private int bestOf = 1;

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

        public Builder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        public Builder suffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        public Builder maxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
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

        public Builder logProbs(int logProbs) {
            this.logProbs = logProbs;
            return this;
        }

        public Builder echo(boolean echo) {
            this.echo = echo;
            return this;
        }

        public Builder stop(List<String> stop) {
            this.stop = stop;
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

        public Builder bestOf(int bestOf) {
            this.bestOf = bestOf;
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

        public CompletionRequest build() {
            return new CompletionRequest(this);
        }
    }

    @Override
    public String toString() {
        return "CompletionRequest{" +
                "model='" + model + '\'' +
                ", prompt='" + prompt + '\'' +
                ", suffix='" + suffix + '\'' +
                ", maxTokens=" + maxTokens +
                ", temperature=" + temperature +
                ", topP=" + topP +
                ", n=" + n +
                ", stream=" + stream +
                ", logProbs=" + logProbs +
                ", echo=" + echo +
                ", stop=" + stop +
                ", presencePenalty=" + presencePenalty +
                ", frequencyPenalty=" + frequencyPenalty +
                ", bestOf=" + bestOf +
                ", logitBias=" + logitBias +
                ", user='" + user + '\'' +
                '}';
    }
}
