package cn.codingguide.chatgpt4j.client;

import cn.codingguide.chatgpt4j.DefaultChatGptClient;
import cn.codingguide.chatgpt4j.constant.EditModel;
import cn.codingguide.chatgpt4j.constant.ImageSize;
import cn.codingguide.chatgpt4j.constant.ResponseFormat;
import cn.codingguide.chatgpt4j.constant.Role;
import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionRequest;
import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionResponse;
import cn.codingguide.chatgpt4j.domain.chat.Message;
import cn.codingguide.chatgpt4j.domain.completions.CompletionRequest;
import cn.codingguide.chatgpt4j.domain.completions.CompletionResponse;
import cn.codingguide.chatgpt4j.domain.edit.EditRequest;
import cn.codingguide.chatgpt4j.domain.edit.EditResponse;
import cn.codingguide.chatgpt4j.domain.images.ImageEditRequest;
import cn.codingguide.chatgpt4j.domain.images.ImageGenerationRequest;
import cn.codingguide.chatgpt4j.domain.images.ImageResponse;
import cn.codingguide.chatgpt4j.domain.images.ImageVariation;
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
                .addMessage(
                        Message.newBuilder().role(Role.USER).content("他到目前为止，出了哪些Java相关的书籍？").build())
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

    @Test
    public void editText() {
        EditRequest request = EditRequest.newBuilder()
                .input("What day of the wek is it?")
                .instruction("Fix the spelling mistakes.")
                .build();
        EditResponse edit = client.edit(request);
        System.out.println(edit.getChoices()[0].getText());
    }

    @Test
    public void editCode() {
        EditRequest request = EditRequest.newBuilder()
                // 修改代码，使用代码通用模型
                .model(EditModel.CODE_DAVINCI_EDIT_001)
                .input("sout(\"Hello World!\")")
                .instruction("帮我修改成正确的Java代码.")
                .build();
        EditResponse edit = client.edit(request);
        System.out.println(edit.getChoices()[0].getText());
    }

    @Test
    public void imageGenerations() {
        ImageGenerationRequest image = ImageGenerationRequest.newBuilder()
                .prompt("给我画一个熟睡的小婴儿。")
                .responseFormat(ResponseFormat.B64_JSON)
                .build();
        ImageResponse imageResponse = client.imageGenerations(image);
        System.out.println(imageResponse);
    }

    @Test
    public void simpleImageGenerations() {
        ImageResponse imageResponse = client.imageGenerations("给我画一个熟睡的小婴儿。");
        System.out.println(imageResponse);
    }

    @Test
    public void simpleImageEdits() {
        ImageResponse imageResponse = client.imageEdits("/Users/xxxx/Desktop/test.png", "请将图片中的英文去掉");
        System.out.println(imageResponse);
    }

    @Test
    public void imageEdits() {
        ImageEditRequest imageEditRequest = ImageEditRequest.newBuilder()
                .image("/Users/xxxx/Desktop/test.png")
                .prompt("请将图片中的英文去掉")
                .size(ImageSize.SIZE_256)
                .user("testUser")
                .build();
        System.out.println(client.imageEdits(imageEditRequest));
    }

    @Test
    public void imageVariations() {
        ImageVariation imageVariation = ImageVariation.newBuilder()
                .image("/Users/xxxx/Desktop/test.png")
                .size(ImageSize.SIZE_256)
                .build();
        System.out.println(client.imageVariations(imageVariation));

    }

}
