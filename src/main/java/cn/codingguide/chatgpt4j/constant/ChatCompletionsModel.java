package cn.codingguide.chatgpt4j.constant;

/**
 * @author itlemon
 * Created on 2023-04-18
 */
public enum ChatCompletionsModel implements ModelSelector {

    /**
     * 当前稳定的ChatCompletions接口适配的模型列表，参考链接：
     * <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">链接</a>
     */
    GPT_4("gpt-4"),
    GPT_4_0314("gpt-4-0314"),
    GPT_4_32K("gpt-4-32k"),
    GPT_4_32K_0314("gpt-4-32k-0314"),
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    GPT_3_5_TURBO_0301("gpt-3.5-turbo-0301");

    private final String model;

    ChatCompletionsModel(String model) {
        this.model = model;
    }

    @Override
    public String getModel() {
        return model;
    }
}
