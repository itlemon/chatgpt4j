package cn.codingguide.chatgpt4j.domain.audio;

import javax.annotation.Nonnull;

import cn.codingguide.chatgpt4j.constant.ModelSelector;
import cn.codingguide.chatgpt4j.constant.TranscriptionModel;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-21
 */
public class TranscriptionRequest {

    private final transient Builder builder;


    private TranscriptionRequest(Builder builder) {
        this.builder = builder;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder;
    }

    public static final class Builder {

        @Nonnull
        private String file;

        @Nonnull
        private ModelSelector model = TranscriptionModel.whisper_1;



    }
}
