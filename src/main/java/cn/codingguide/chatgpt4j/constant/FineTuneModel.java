package cn.codingguide.chatgpt4j.constant;

/**
 * @author itlemon
 * Created on 2023-04-24
 */
public enum FineTuneModel implements ModelSelector {
    /**
     * 模型列表：<a href="https://platform.openai.com/docs/api-reference/fine-tunes/create#fine-tunes/create-model">链接</a>
     * 默认是：curie
     */
    ADA("ada"),
    BABBAGE("babbage"),
    CURIE("curie"),
    DAVINCI("davinci");

    private final String model;

    FineTuneModel(String model) {
        this.model = model;
    }

    @Override
    public String getModel() {
        return model;
    }
}
