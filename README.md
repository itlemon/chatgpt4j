# chatgpt4j 项目简介

chatgpt4j 是一个用于访问 ChatGPT API 的 Java 客户端库，支持 OpenAI 全部的[接口](https://platform.openai.com/docs/api-reference/introduction)，几行代码就可以帮助用户实现将 ChatGPT 快速接入到自己的项目中。

## 特性⛳️

- 支持 OpenAI 提供的全部接口
- 支持多 ApiKey 模式
- 支持多 ApiKey 随机选择策略及自定义选择策略
- 支持自定义 OkHttpClient
- 支持自定义 Http 请求过程的日志级别
- 支持自定义模型定义，方便后续在未更新该 SDK 的情况下，也可以使用到最新的 OpenAI 模型
- 支持接口请求参数使用 Builder 模型来构建，极大地简化参数构建过程
- 支持 Http 或者 Socks 代理模式，解决大陆无法访问 OpenAI 接口的问题
- 更多特性，敬请期待...

## 更新日志📝

- [x] 1.0.1 将 KeySelectorStrategy 设置为函数式接口
- [x] 1.0.0 支持 OpenAI 提供的全部接口

## 接口支持🧩

目前支持官方全部的接口，具体描述如下表所示：

|        接口        | 接口说明                                                                                                         | 接口列表                                                                                                        | 是否支持 |
|:----------------:|:-------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------|:----:|
|      Models      | 列出并描述 API 中可用的各种模型，您可以参考[模型文档](https://platform.openai.com/docs/models)以了解可用的模型以及它们之间的区别。                    | 1.获取所有模型详情列表<br />2.根据模型 ID 检索某个模型详情                                                                        |  ✅   |
|   Completions    | 给定一个提示，模型将返回一个或多个预测的完成结果，并可以在每个位置返回替代标记的概率。Completions 类任务，通俗点理解的话，完形填空、句子补齐、写作文、翻译都算这类任务，它按照的你要求帮你生成你需要的结果。 | 1.创建 completion                                                                                             |  ✅   |
| Chat Completions | 给定一个描述对话的消息列表，该模型将返回一个响应。Chat Completions 类任务，通俗点理解的话，可以设置一些角色情景，实现连续对话，聊天式对话等。                              | 1.创建 chat completion                                                                                        |  ✅   |
|      Edits       | 给定提示和指令，模型将返回按照提示的要求完成文本等内容的编辑。                                                                              | 1.创建 edits                                                                                                  |  ✅   |
|      Images      | 给定提示和指令，模型将返回按照提示的要求创建图像、编辑图像、根据已有图像创建变体等操作的结果。                                                              | 1.根据提示创建图像<br />2.根据提示编辑图像<br />3.根据提示创建指定图像的变体                                                             |  ✅   |
|    Embeddings    | 获取一个给定输入的向量表示，该向量可以轻松地被机器学习模型和算法所使用。                                                                         | 1.创建表示输入文本的嵌入向量                                                                                             |  ✅   |
|      Audio       | 给定一段音频，模型将音频转成文字。                                                                                            | 1.将音频转录为输入语言<br />2.将音频翻译成英文                                                                                |  ✅   |
|      Files       | 文件管理相关接口，这些文件上传后可与微调等功能一起使用，用于训练特定模型。                                                                        | 1.获取属于用户组织的文件列表<br />2.上传文件<br />3.删除文件<br />4.检索文件<br />5.检索文件内容（收费接口）                                     |  ✅   |
|    Fine-Tunes    | 根据已经上传的训练数据文件和已有模型来训练特定的模型，这类接口用于管理 Fine-Tunes 任务。                                                           | 1.创建一个从给定数据集微调指定模型的作业<br />2.列出属于用户组织的微调作业<br />3.检索微调作业<br />4.取消一个微调作业<br />5.获取微调作业的事件状态<br />6.删除微调后的模型 |  ✅   |
|   Moderations    | Moderation 用来审查内容是否符合 OpenAI 的内容政策。                                                                          | 1.创建一个文本审查任务                                                                                                |  ✅   |
|     Engines      | 管理引擎的相关接口，这类接口已经废弃，目前由 Models 来代替。                                                                           | 1.获取已有引擎列表<br />2.检索引擎                                                                                      |  ✅   |

## 快速开始🚀

本项目所有的接口都经过了详细的测试，读者可以参考测试类：[cn.codingguide.chatgpt4j.client.DefaultChatGptClientTest](https://github.com/itlemon/chatgpt4j/blob/master/src/test/java/cn/codingguide/chatgpt4j/client/DefaultChatGptClientTest.java)

### 1.导入pom依赖

目前最新版本是 1.0.1，读者可以从[中央仓库](https://mvnrepository.com/artifact/cn.codingguide/chatgpt4j)搜索最新版本导入到pom中即可。

```xml
<dependency>
    <groupId>cn.codingguide</groupId>
    <artifactId>chatgpt4j</artifactId>
    <version>1.0.1</version>
</dependency>
```

或者直接 clone 本项目到本地，将其安装到本地仓库中。

### 2.默认客户端使用案例

默认客户端请参考代码：[cn.codingguide.chatgpt4j.DefaultChatGptClient](https://github.com/itlemon/chatgpt4j/blob/master/src/main/java/cn/codingguide/chatgpt4j/DefaultChatGptClient.java)

创建一个默认的客户端，代码案例如下：

```java
public class DefaultChatGptClientTest {

    private DefaultChatGptClient client;

    @Before
    public void setUp() {
        client = DefaultChatGptClient.newBuilder()
                // 这里替换成自己的key，该参数是必填项
                .apiKeys(Arrays.asList("sk-******"))
                // 设置apiHost，如果没有自己的api地址，可以不用设置，默认是：https://api.openai.com/
                .apiHost("https://xxxxx/")
                // 设置proxy代理，方便大陆通过代理访问OpenAI，支持Http代理或者Socks代理，两者只需要设置其一即可，两者都设置，后者将覆盖前者
                .proxyHttp("127.0.0.1", 8080)
                .proxySocks("127.0.0.1", 8081)
                // 支持自定义OkHttpClient，该参数非必填，没有填写将使用默认的OkHttpClient
                .okHttpClient(null)
                // 设置apiKey选择策略，该参数是非必填项，如果没有填写，将使用默认的随机选择器（RandomKeySelectorStrategy），用户可以通过实现KeySelectorStrategy接口提供自定义选择器
                .keySelectorStrategy(new RandomKeySelectorStrategy())
                // 设置开启日志，非必填项，默认没有打印请求日志，测试期间可以设置BODY日志，日志量较大，生产环境不建议开启
                .logLevel(HttpLoggingInterceptor.Level.BODY)
                .build();
    }

}
```

上述案例中，OkHttpClient 没有设置（或者设置为null）将采用默认的 OkHttpClient，该 Client 配置如下所示：

```java
private OkHttpClient okHttpClient() {
    return new OkHttpClient.Builder()
        // 默认客户端没有代理
        .proxy(Proxy.NO_PROXY)
        .addInterceptor(new ResponseInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build();
}
```

该默认的 Client 中，各类超时时间设置的是 30s，且没有设置代理，代理由 DefaultChatGptClient 中的 proxyHttp 或者 proxySocks 来设置，同理，http 请求日志级别，也由 DefaultChatGptClient 中的 logLevel 来统一设置。

用户如果有需要自定义 OkHttpClient，那么只需要简单定义一个 OkHttpClient 即可，通过 DefaultChatGptClient 中的 okHttpClient 方法设置进来。

```java
OkHttpClient okHttpClient = new OkHttpClient
    .Builder()
    // 自定义拦截器Interceptor，实现请求前后的拦截
    .addInterceptor(Interceptor)
    // 自定义超时时间
    .connectTimeout(10, TimeUnit.SECONDS)
    .writeTimeout(10, TimeUnit.SECONDS)
    .readTimeout(10, TimeUnit.SECONDS)
    .build();
```

DefaultChatGptClient 是一个线程安全的类，可以全局保留一个即可，可以在项目启动的时候创建一个缓存起来，后续复用即可。最简单的客户端构建代码如下所示：

```java
public class DefaultChatGptClientTest {

    private DefaultChatGptClient client;

    @Before
    public void setUp() {
        client = DefaultChatGptClient.newBuilder()
                // 这里替换成自己的key，该参数是必填项
                .apiKeys(Arrays.asList("sk-******"))
                .build();
    }

}
```

### 3.接口使用案例

下面的接口案例中，对每一个接口都进行了测试，读者可以 clone 代码到本地，自行测试使用。

```java
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
```
