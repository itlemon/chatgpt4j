package cn.codingguide.chatgpt4j.constant;

/**
 * @author itlemon
 * Created on 2023-04-21
 */
public enum TranscriptionModel implements ModelSelector {

    /**
     * 当前稳定的Completions接口适配的模型列表，参考链接：
     * <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">链接</a>
     */
    WHISPER_1("whisper-1");

    private final String model;

    TranscriptionModel(String model) {
        this.model = model;
    }

    @Override
    public String getModel() {
        return model;
    }
}
