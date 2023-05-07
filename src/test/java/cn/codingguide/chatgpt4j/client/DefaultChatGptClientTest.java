package cn.codingguide.chatgpt4j.client;

import cn.codingguide.chatgpt4j.DefaultChatGptClient;
import cn.codingguide.chatgpt4j.constant.*;
import cn.codingguide.chatgpt4j.domain.audio.TranscriptionRequest;
import cn.codingguide.chatgpt4j.domain.audio.TranslationRequest;
import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionRequest;
import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionResponse;
import cn.codingguide.chatgpt4j.domain.chat.Message;
import cn.codingguide.chatgpt4j.domain.completions.CompletionRequest;
import cn.codingguide.chatgpt4j.domain.completions.CompletionResponse;
import cn.codingguide.chatgpt4j.domain.edits.EditRequest;
import cn.codingguide.chatgpt4j.domain.edits.EditResponse;
import cn.codingguide.chatgpt4j.domain.finetune.FineTuneRequest;
import cn.codingguide.chatgpt4j.domain.images.ImageEditRequest;
import cn.codingguide.chatgpt4j.domain.images.ImageGenerationRequest;
import cn.codingguide.chatgpt4j.domain.images.ImageResponse;
import cn.codingguide.chatgpt4j.domain.images.ImageVariationRequest;
import cn.codingguide.chatgpt4j.domain.models.Model;

import cn.codingguide.chatgpt4j.domain.moderations.ModerationRequest;
import cn.codingguide.chatgpt4j.key.RandomKeySelectorStrategy;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author itlemon
 * Created on 2023-04-05
 */
public class DefaultChatGptClientTest {

    private DefaultChatGptClient client;

    @Before
    public void setUp() {
        client = DefaultChatGptClient.newBuilder()
                // 这里替换成自己的key，该参数是必填项
                .apiKeys(Arrays.asList("sk-******"))
                // 设置apiHost，如果没有自己的api地址，可以不用设置，默认是：https://api.openai.com/
                // .apiHost("https://xxxxx/")
                // 设置proxy代理，方便大陆通过代理访问OpenAI，支持Http代理或者Socks代理，两者只需要设置其一即可，两者都设置，后者将覆盖前者
                // .proxyHttp("127.0.0.1", 8080)
                // .proxySocks("127.0.0.1", 8081)
                // 支持自定义OkHttpClient，该参数非必填，没有填写将使用默认的OkHttpClient
                // .okHttpClient(null)
                // 设置apiKey选择策略，该参数是非必填项，如果没有填写，将使用默认的随机选择器（RandomKeySelectorStrategy），用户可以通过实现KeySelectorStrategy接口提供自定义选择器
                // .keySelectorStrategy(new RandomKeySelectorStrategy())
                // 设置开启日志，非必填项，默认没有打印请求日志，测试期间可以设置BODY日志，日志量较大，生产环境不建议开启
                .logLevel(HttpLoggingInterceptor.Level.BODY)
                .build();
    }

    @Test
    public void models() {
        client.models().forEach(e -> {
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
                .responseFormat(ImageResponseFormat.B64_JSON)
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
        ImageVariationRequest imageVariation = ImageVariationRequest.newBuilder()
                .image("/Users/xxxx/Desktop/test.png")
                .size(ImageSize.SIZE_256)
                .build();
        System.out.println(client.imageVariations(imageVariation));

    }

    @Test
    public void simpleEmbeddings() {
        System.out.println(client.embeddings("我是中国人，我爱你中国！"));
    }

    @Test
    public void simpleEmbeddings1() {
        System.out.println(client.embeddings(Arrays.asList("我是中国人，我爱你中国！", "I love you! China.")));
    }

    @Test
    public void simpleSpeechToTextTranscriptions() {
        System.out.println(client.speechToTextTranscriptions("/Users/xxxx/Desktop/german.m4a"));
    }

    @Test
    public void speechToTextTranscriptions() {
        TranscriptionRequest build = TranscriptionRequest.newBuilder()
                .file("/Users/xxxx/Desktop/german.m4a")
                .model(TranscriptionModel.WHISPER_1)
                .prompt("将音频内容转换成中文")
                .language("zh")
                .temperature(0.2)
                .responseFormat(TranscriptionResponseFormat.JSON)
                .build();
        System.out.println(client.speechToTextTranscriptions(build));
    }

    @Test
    public void simpleSpeechToTextTranslations() {
        System.out.println(client.speechToTextTranslations("/Users/xxxx/Desktop/german.m4a"));
    }

    @Test
    public void speechToTextTranslations() {
        TranslationRequest build = TranslationRequest.newBuilder()
                .file("/Users/xxxx/Desktop/german.m4a")
                .model(TranscriptionModel.WHISPER_1)
                .prompt("Please translate the audio content into English.")
                .temperature(0.2)
                .responseFormat(TranscriptionResponseFormat.JSON)
                .build();
        System.out.println(client.speechToTextTranslations(build));
    }

    @Test
    public void files() {
        System.out.println(client.files());
    }

    @Test
    public void uploadFile() {
        String filePath = "/Users/xxxx/Desktop/test.txt";
        System.out.println(client.uploadFile(filePath));
    }

    @Test
    public void uploadFileWithPurpose() {
        String filePath = "/Users/xxxx/Desktop/test.txt";
        System.out.println(client.uploadFile(filePath, "fine-tune"));
    }

    @Test
    public void deleteFile() {
        System.out.println(client.deleteFile("file-y8EycSoRW3VZUOVXRMqJYQAP"));
    }

    @Test
    public void retrieveFile() {
        System.out.println(client.retrieveFile("file-75dy5RmHbcM7aO4zjZLEv4FC"));
    }

    @Test
    public void retrieveFileContent() {
        // 该接口非plus无法调用，会返回：To help mitigate abuse, downloading of fine-tune training files is disabled for free accounts.
        System.out.println(client.retrieveFileContent("file-75dy5RmHbcM7aO4zjZLEv4FC"));
    }

    @Test
    public void simpleFineTune() {
        System.out.println(client.fineTune("file-ZX7aR5aNF0ejVYFM8bCN9zNW"));
    }

    @Test
    public void fineTune() {
        FineTuneRequest fineTuneRequest = FineTuneRequest.newBuilder()
                .trainingFile("file-ZX7aR5aNF0ejVYFM8bCN9zNW")
                .suffix("codingguide-cn")
                .model(FineTuneModel.DAVINCI)
                .build();
        System.out.println(client.fineTune(fineTuneRequest));
    }

    @Test
    public void fineTunes() {
        System.out.println(client.fineTunes());
    }

    @Test
    public void retrieveFineTune() {
        System.out.println(client.retrieveFineTune("ft-HP1rZOIARmWUlcAaPi4obN8O"));
    }

    @Test
    public void cancelFineTune() {
        System.out.println(client.cancelFineTune("ft-iLDdAoQvKH777yNvBVfGUoGq"));
    }

    @Test
    public void fineTuneEvents() {
        System.out.println(client.fineTuneEvents("ft-iLDdAoQvKH777yNvBVfGUoGq"));
    }

    @Test
    public void deleteFineTuneModel() {
        System.out.println(client.deleteFineTuneModel(""));
    }

    @Test
    public void simpleModerations() {
        System.out.println(client.moderations("I want to kill them."));
    }

    @Test
    public void simpleModerations1() {
        System.out.println(client.moderations(Arrays.asList("I want to kill them.", "I love you.")));
    }

    @Test
    public void moderationss() {
        ModerationRequest moderationRequest = ModerationRequest.newBuilder()
                .addInput("I want to kill them.")
                .addInput("I love you.")
                .model(ModerationModel.TEXT_MODERATION_LATEST)
                .build();
        System.out.println(client.moderations(moderationRequest));
    }

    @Test
    public void engines() {
        System.out.println(client.engines());
    }

    @Test
    public void engine() {
        System.out.println(client.engine("text-davinci-edit-001"));
    }

}
