package cn.codingguide.chatgpt4j.constant;

/**
 * @author itlemon
 * Created on 2023-04-20
 */
public enum EmbeddingsModel implements ModelSelector {

    /**
     * 当前稳定的Embeddings接口适配的模型列表，参考链接：
     * <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">链接</a>
     */
    TEXT_EMBEDDING_ADA_002("text-embedding-ada-002"),
    TEXT_SEARCH_ADA_DOC_001("text-search-ada-doc-001");

    private final String model;

    EmbeddingsModel(String model) {
        this.model = model;
    }

    @Override
    public String getModel() {
        return model;
    }
}
