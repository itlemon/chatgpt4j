package cn.codingguide.chatgpt4j.domain.completions;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-05
 */
public class CompletionRequest implements Serializable {




    public static final class Builder {

        @NotNull
        private String model;

        @NotNull
        private String prompt;

        private String suffix;

        private Integer maxTokens;

        private Double temperature;

        private Double topP;

        private Integer countOfCompletion4EachPrompt;

        private boolean stream;

        private Integer logprobs;

        private boolean echo;



    }

}
