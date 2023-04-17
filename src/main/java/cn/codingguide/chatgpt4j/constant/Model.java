package cn.codingguide.chatgpt4j.constant;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-17
 */
public enum Model implements ModelSelector {
    /**
     * 当前稳定的GPT-3.5模型列表
     */
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    GPT_3_5_TURBO_0301("gpt-3.5-turbo-0301"),
    TEXT_DAVINCI_003("text-davinci-003"),
    TEXT_DAVINCI_002("text-davinci-002"),
    CODE_DAVINCI_002("code-davinci-002");

    private final String model;

    Model(String model) {
        this.model = model;
    }

    @Override
    public String getModel() {
        return model;
    }
}
