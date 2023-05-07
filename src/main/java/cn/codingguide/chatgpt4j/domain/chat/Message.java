package cn.codingguide.chatgpt4j.domain.chat;

import java.io.Serializable;

import javax.annotation.Nonnull;

import cn.codingguide.chatgpt4j.constant.Role;

/**
 * @author itlemon
 * Created on 2023-04-18
 */
public class Message implements Serializable {

    private final transient Builder builder;

    private final String role;

    private final String content;

    private final String name;

    private Message(Builder builder) {
        this.builder = builder;
        this.role = builder.role.name().toLowerCase();
        this.content = builder.content;
        this.name = builder.name;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder;
    }

    public Builder getBuilder() {
        return builder;
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public static final class Builder {

        /**
         * OPENAI官网仅支持三种角色，分别是：system、user、assistant，参考<a href="https://platform.openai.com/docs/guides/chat">链接</a>
         */
        @Nonnull
        private Role role;

        @Nonnull
        private String content;

        private String name;

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "builder=" + builder +
                ", role='" + role + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
