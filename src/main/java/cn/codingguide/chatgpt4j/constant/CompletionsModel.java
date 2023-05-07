package cn.codingguide.chatgpt4j.constant;

/**
 * @author itlemon
 * Created on 2023-04-17
 */
public enum CompletionsModel implements ModelSelector {

    /**
     * 当前稳定的Completions接口适配的模型列表，参考链接：
     * <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">链接</a>
     */
    TEXT_DAVINCI_003("text-davinci-003"),
    TEXT_DAVINCI_002("text-davinci-002"),
    TEXT_CURIE_001("text-curie-001"),
    TEXT_BABBAGE_001("text-babbage-001"),
    TEXT_ADA_001("text-ada-001");

    private final String model;

    CompletionsModel(String model) {
        this.model = model;
    }

    @Override
    public String getModel() {
        return model;
    }
}
