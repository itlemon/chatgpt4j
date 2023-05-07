package cn.codingguide.chatgpt4j.constant;

/**
 * 模型选择器，可实现该接口实现模型选择
 *
 * @author itlemon
 * Created on 2023-04-17
 */
public interface ModelSelector {

    /**
     * 获取模型，当前稳定版本的模型：<a href="https://platform.openai.com/docs/models/gpt-3-5">链接</a>
     *
     * @return 模型
     */
    String getModel();

}
