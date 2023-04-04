package cn.codingguide.chatgpt4j;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import cn.codingguide.chatgpt4j.api.OpenAiApi;
import cn.codingguide.chatgpt4j.key.KeySelectorStrategy;
import okhttp3.OkHttpClient;

/**
 * 默认的ChatGPT客户端
 *
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-04
 */
public class DefaultChatGptClient {

    /**
     * API KEY列表，不可为null，不可为空
     */
    @NotNull
    private final List<String> apiKeys;

    /**
     * API Host，默认为：https://api.openai.com，可设置代理
     */
    private final String apiHost;

    /**
     * API
     */
    private final OpenAiApi api;

    /**
     * okHttpClient，支持自定义
     */
    private final OkHttpClient okHttpClient;

    /**
     * API KEY的选择器，默认实现是从列表中随机选择，用户可自定义选择逻辑
     */
    private final KeySelectorStrategy<List<String>, String> keySelectorStrategy;


    public static final class Builder {

        private List<String> apiKeys;

        private String apiHost;

        private OkHttpClient okHttpClient;

        private KeySelectorStrategy<List<String>, String> keySelectorStrategy;

        public Builder() {
        }



    }

}
