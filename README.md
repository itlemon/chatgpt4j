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
- 支持代理模式，解决大陆无法访问 OpenAI 接口的问题
- 更多特性，敬请期待...

## 更新日志📝

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

目前最新版本是 1.0.0，读者可以从[中央仓库](https://mvnrepository.com/artifact/cn.codingguide/chatgpt4j)搜索最新版本导入到pom中即可。

```xml
<dependency>
    <groupId>cn.codingguide</groupId>
    <artifactId>chatgpt4j</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2.默认客户端使用案例

默认客户端请参考代码：[cn.codingguide.chatgpt4j.DefaultChatGptClient](https://github.com/itlemon/chatgpt4j/blob/master/src/main/java/cn/codingguide/chatgpt4j/DefaultChatGptClient.java)

