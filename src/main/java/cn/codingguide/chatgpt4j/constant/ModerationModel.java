package cn.codingguide.chatgpt4j.constant;

/**
 * @author itlemon
 * Created on 2023-04-28
 */
public enum ModerationModel implements ModelSelector {

    /**
     * 当前稳定的Completions接口适配的模型列表，参考链接：
     * <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">链接</a>
     */
    TEXT_MODERATION_STABLE("text-moderation-stable"),
    TEXT_MODERATION_LATEST("text-moderation-latest");

    private final String model;

    ModerationModel(String model) {
        this.model = model;
    }

    @Override
    public String getModel() {
        return model;
    }
}
