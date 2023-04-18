package cn.codingguide.chatgpt4j.client;

import cn.codingguide.chatgpt4j.DefaultChatGptClient;
import cn.codingguide.chatgpt4j.constant.Role;
import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionRequest;
import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionResponse;
import cn.codingguide.chatgpt4j.domain.chat.Message;
import cn.codingguide.chatgpt4j.domain.completions.CompletionRequest;
import cn.codingguide.chatgpt4j.domain.completions.CompletionResponse;
import cn.codingguide.chatgpt4j.domain.models.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-05
 */
public class DefaultChatGptClientTest {

    private DefaultChatGptClient client;

    @Before
    public void setUp() {
        client = DefaultChatGptClient.newBuilder()
                // 这里替换成自己的key
                .apiKeys(Arrays.asList("sk-****"))
                .enableHttpDetailLog(true)
                .build();
    }

    @Test
    public void models() {
        List<Model> models = client.models();
        models.forEach(e -> {
            System.out.print(e.getId() + " ");
            System.out.print(e.getOwnedBy() + " ");
            System.out.print(e.getObject() + " ");
            System.out.println(e.getCreated() + " ");
        });
    }

    @Test
    public void model() {
        Model model = client.model("text-search-ada-query-001");
        System.out.println(model);
    }

    @Test
    public void simpleCompletions() {
        CompletionResponse completions = client.completions("可以帮我介绍一下《三体》这部电视剧吗？");
        System.out.println(completions);
    }

    @Test
    public void continuousCompletions() {
        CompletionRequest question = CompletionRequest.newBuilder()
                .prompt("帮我查一下世界上综合实力最强的前三名大学名称？\n")
                .echo(true)
                .build();
        CompletionResponse completions = client.completions(question);
        String text = completions.getChoices()[0].getText();

        question = question.toBuilder()
                .prompt(text + "\n\n" + "前五名呢？\n")
                .build();
        completions = client.completions(question);
        text = completions.getChoices()[0].getText();

        question = question.toBuilder()
                .prompt(text + "\n\n" + "前十名呢？\n")
                .build();
        completions = client.completions(question);
        text = completions.getChoices()[0].getText();

        question = question.toBuilder()
                .prompt(text + "\n\n" + "这前十名高校中，美国占了几所？分别是哪些？\n")
                .build();
        completions = client.completions(question);
        text = completions.getChoices()[0].getText();
        System.out.println(text);
    }

    @Test
    public void chatCompletions() {
        ChatCompletionRequest question = ChatCompletionRequest.newBuilder()
                .addMessage(Message.newBuilder().role(Role.SYSTEM).content("假设你是一名Java开发工程师！").build())
                .addMessage(Message.newBuilder().role(Role.USER).content("考你一个问题：你知道中国的Java之父是谁吗？")
                        .build())
                .build();
        ChatCompletionResponse chatCompletion = client.chatCompletions(question);
        question = question.toBuilder()
                .addMessage(Message.newBuilder().role(Role.ASSISTANT)
                        .content(chatCompletion.getChoices()[0].getMessage().getContent()).build())
                .addMessage(Message.newBuilder().role(Role.USER).content("他到目前为止，出了哪些Java相关的书籍？").build())
                .build();
        chatCompletion = client.chatCompletions(question);

        // 格式化输出整个过程
        List<Message> messages = question.getMessages();
        for (Message message : messages) {
            System.out.println(message.getContent());
            System.out.println();
        }

        System.out.println(chatCompletion.getChoices()[0].getMessage().getContent());
    }

}
