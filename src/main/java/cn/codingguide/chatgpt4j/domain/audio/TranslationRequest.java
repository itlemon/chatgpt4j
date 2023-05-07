package cn.codingguide.chatgpt4j.domain.audio;

import org.jetbrains.annotations.NotNull;

import cn.codingguide.chatgpt4j.constant.ModelSelector;
import cn.codingguide.chatgpt4j.constant.TranscriptionModel;
import cn.codingguide.chatgpt4j.constant.TranscriptionResponseFormat;

/**
 * @author itlemon
 * Created on 2023-04-21
 */
public class TranslationRequest {

    private final transient Builder builder;
    private final String file;
    private final String model;
    private final String prompt;
    private final String responseFormat;
    private final Double temperature;

    private TranslationRequest(Builder builder) {
        this.builder = builder;
        this.file = builder.file;
        this.model = builder.model.getModel();
        this.prompt = builder.prompt;
        this.responseFormat = builder.responseFormat.name().toLowerCase();
        this.temperature = builder.temperature;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder;
    }

    public String getFile() {
        return file;
    }

    public String getModel() {
        return model;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getResponseFormat() {
        return responseFormat;
    }

    public Double getTemperature() {
        return temperature;
    }

    public static final class Builder {

        /**
         * 必需参数：要转录的音频文件，采用以下格式之一：mp3、mp4、mpeg、mpga、m4a、wav 或 webm
         */
        @NotNull
        private String file;

        /**
         * 必需参数：ID of the model to use. Only whisper-1 is currently available.
         */
        @NotNull
        private ModelSelector model = TranscriptionModel.WHISPER_1;

        /**
         * 非必需参数：可选文本，用于指导模型的风格或继续之前的音频片段。 提示应与音频语言相匹配
         */
        private String prompt;

        /**
         * 非必需参数：The format of the transcript output, in one of these options: json, text, srt, verbose_json, or vtt.
         * json is default.
         */
        private TranscriptionResponseFormat responseFormat = TranscriptionResponseFormat.JSON;

        /**
         * 非必需参数：生成结果的多样性程度，使用什么采样温度，介于 0 和 2 之间。较高的值（如 0.8）将使输出更加随机，而较低的值（如 0.2）将使输出更加集中和确定。
         * 我们通常建议更改此设置，但不要同时更改 top_p
         */
        private Double temperature = 0D;

        private Builder() {
        }

        public Builder file(String file) {
            this.file = file;
            return this;
        }

        public Builder model(ModelSelector model) {
            this.model = model;
            return this;
        }

        public Builder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        public Builder responseFormat(TranscriptionResponseFormat responseFormat) {
            this.responseFormat = responseFormat;
            return this;
        }

        public Builder temperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public TranslationRequest build() {
            return new TranslationRequest(this);
        }

    }

    @Override
    public String toString() {
        return "TranscriptionRequest{" +
                "file='" + file + '\'' +
                ", model='" + model + '\'' +
                ", prompt='" + prompt + '\'' +
                ", responseFormat='" + responseFormat + '\'' +
                ", temperature=" + temperature +
                '}';
    }
}
