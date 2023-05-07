package cn.codingguide.chatgpt4j.constant;

/**
 * @author itlemon
 * Created on 2023-04-19
 */
public enum EditModel implements ModelSelector {

    /**
     * 当前稳定的Completions接口适配的模型列表，参考链接：
     * <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">链接</a>
     */
    TEXT_DAVINCI_EDIT_001("text-davinci-edit-001"),
    CODE_DAVINCI_EDIT_001("code-davinci-edit-001");

    private final String model;

    EditModel(String model) {
        this.model = model;
    }

    @Override
    public String getModel() {
        return model;
    }
}
