package cn.codingguide.chatgpt4j.api;

import java.util.Map;

import cn.codingguide.chatgpt4j.domain.audio.TranscriptionResponse;
import cn.codingguide.chatgpt4j.domain.audio.TranslationResponse;
import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionRequest;
import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionResponse;
import cn.codingguide.chatgpt4j.domain.common.CommonListResponse;
import cn.codingguide.chatgpt4j.domain.completions.CompletionRequest;
import cn.codingguide.chatgpt4j.domain.completions.CompletionResponse;
import cn.codingguide.chatgpt4j.domain.edits.EditRequest;
import cn.codingguide.chatgpt4j.domain.edits.EditResponse;
import cn.codingguide.chatgpt4j.domain.embeddings.EmbeddingRequest;
import cn.codingguide.chatgpt4j.domain.embeddings.EmbeddingResponse;
import cn.codingguide.chatgpt4j.domain.engines.EngineItem;
import cn.codingguide.chatgpt4j.domain.files.FileItem;
import cn.codingguide.chatgpt4j.domain.files.FileResponse;
import cn.codingguide.chatgpt4j.domain.finetune.*;
import cn.codingguide.chatgpt4j.domain.images.ImageGenerationRequest;
import cn.codingguide.chatgpt4j.domain.images.ImageResponse;
import cn.codingguide.chatgpt4j.domain.models.Model;
import cn.codingguide.chatgpt4j.domain.moderations.ModerationRequest;
import cn.codingguide.chatgpt4j.domain.moderations.ModerationResponse;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * @author itlemon
 * Created on 2023-04-04
 */
public interface OpenAiApi {

    String OPEN_AI_HOST = "https://api.openai.com/";

    /**
     * <a href="https://platform.openai.com/docs/api-reference/models/list">获取Open AI模型列表</a>
     *
     * @return model列表
     */
    @GET("v1/models")
    Single<CommonListResponse<Model>> models();

    /**
     * <a href="https://platform.openai.com/docs/api-reference/models/retrieve">获取Open AI模型详情</a>
     *
     * @param id model Id
     * @return model详情
     */
    @GET("v1/models/{model}")
    Single<Model> model(@Path("model") String id);

    /**
     * 动态交流，问答等
     * Given a prompt, the model will return one or more predicted completions,
     * and can also return the probabilities of alternative tokens at each position.
     * <a href="https://platform.openai.com/docs/api-reference/completions/create">链接</a>
     *
     * @param completion 请求参数
     * @return 返回回答内容
     */
    @POST("v1/completions")
    Single<CompletionResponse> completions(@Body CompletionRequest completion);

    /**
     * 为给定的聊天对话创建模型响应，默认模型：gpt-3.5-turbo，和官网chat窗口问答一致
     *
     * @param chatCompletion 请求参数
     * @return 返回问答内容
     */
    @POST("v1/chat/completions")
    Single<ChatCompletionResponse> chatCompletion(@Body ChatCompletionRequest chatCompletion);

    /**
     * Creates a new edit for the provided input, instruction, and parameters.
     * 文本编辑修改
     *
     * @param edit 请求参数
     * @return 编辑返回体
     */
    @POST("v1/edits")
    Single<EditResponse> edits(@Body EditRequest edit);

    /**
     * 根据描述生成图片
     *
     * @param image 请求参数
     * @return 图片列表
     */
    @POST("v1/images/generations")
    Single<ImageResponse> imageGenerations(@Body ImageGenerationRequest image);

    /**
     * 编辑图片
     *
     * @param image          图片
     * @param mask           遮罩层
     * @param requestBodyMap 描述等参数
     * @return 编辑后的图片
     */
    @Multipart
    @POST("v1/images/edits")
    Single<ImageResponse> imageEdits(
            @Part() MultipartBody.Part image,
            @Part() MultipartBody.Part mask,
            @PartMap() Map<String, RequestBody> requestBodyMap);

    /**
     * 创建给定图像的变体
     *
     * @param image          图片
     * @param requestBodyMap 请求参数
     * @return ImageResponse
     */
    @Multipart
    @POST("v1/images/variations")
    Single<ImageResponse> imageVariations(
            @Part() MultipartBody.Part image,
            @PartMap() Map<String, RequestBody> requestBodyMap);

    /**
     * 文本向量计算
     *
     * @param embedding 请求参数
     * @return 计算结果
     */
    @POST("v1/embeddings")
    Single<EmbeddingResponse> embeddings(@Body EmbeddingRequest embedding);

    /**
     * 语音转文字
     *
     * @param audioFile      语音文件
     * @param requestBodyMap 请求参数
     * @return 转换结果，文本
     */
    @Multipart
    @POST("v1/audio/transcriptions")
    Single<TranscriptionResponse> speechToTextTranscriptions(@Part MultipartBody.Part audioFile,
                                                             @PartMap() Map<String, RequestBody> requestBodyMap);

    /**
     * 语音翻译：目前仅支持翻译为英文
     *
     * @param audioFile      语音文件
     * @param requestBodyMap 请求参数
     * @return 翻译后的文本
     */
    @Multipart
    @POST("v1/audio/translations")
    Single<TranslationResponse> speechToTextTranslations(@Part MultipartBody.Part audioFile,
                                                         @PartMap() Map<String, RequestBody> requestBodyMap);

    /**
     * 获取文件列表
     *
     * @return 文件列表
     */
    @GET("v1/files")
    Single<FileResponse> files();

    /**
     * 上传文件
     *
     * @param purpose 目的
     * @param file    文件
     * @return 上传结果
     */
    @Multipart
    @POST("v1/files")
    Single<FileItem> uploadFile(@Part MultipartBody.Part file, @Part("purpose") RequestBody purpose);

    /**
     * 删除文件
     *
     * @param fileId 文件ID
     * @return 删除结果
     */
    @DELETE("v1/files/{file_id}")
    Single<FileItem> deleteFile(@Path("file_id") String fileId);

    /**
     * 检索文件
     *
     * @param fileId 文件ID
     * @return 文件
     */
    @GET("v1/files/{file_id}")
    Single<FileItem> retrieveFile(@Path("file_id") String fileId);

    /**
     * 检索文件内容，付费plus用户专享接口
     *
     * @param fileId 文件ID
     * @return ResponseBody
     */
    @Streaming
    @GET("v1/files/{file_id}/content")
    Single<ResponseBody> retrieveFileContent(@Path("file_id") String fileId);

    /**
     * 根据已有模型进行微调创建新模型
     *
     * @param fineTune 微调作业参数
     * @return 创建结果
     */
    @POST("v1/fine-tunes")
    Single<FineTuneResponse> fineTune(@Body FineTuneRequest fineTune);

    /**
     * 获取微调作业列表
     *
     * @return 微调作业列表
     */
    @GET("v1/fine-tunes")
    Single<CommonListResponse<FineTuneResponse>> fineTunes();

    /**
     * 检索微调作业
     *
     * @param fineTuneId 微调作业ID
     * @return 作业详情
     */
    @GET("v1/fine-tunes/{fine_tune_id}")
    Single<FineTuneResponse> retrieveFineTune(@Path("fine_tune_id") String fineTuneId);

    /**
     * 取消微调作业
     *
     * @param fineTuneId 微调作业ID
     * @return 作业详情
     */
    @POST("v1/fine-tunes/{fine_tune_id}/cancel")
    Single<FineTuneResponse> cancelFineTune(@Path("fine_tune_id") String fineTuneId);

    /**
     * 微调作业事件列表
     *
     * @param fineTuneId 微调作业ID
     * @return 微调作业事件列表
     */
    @GET("v1/fine-tunes/{fine_tune_id}/events")
    Single<CommonListResponse<FineTuneEvent>> fineTuneEvents(@Path("fine_tune_id") String fineTuneId);

    /**
     * 删除微调作业模型，只能删除自己的组织内的作业模型
     *
     * @param model 模型ID
     * @return 删除结果
     */
    @DELETE("v1/models/{model}")
    Single<FineTuneDeleteResponse> deleteFineTuneModel(@Path("model") String model);

    /**
     * 文本审核与分类，主要用于检测文本是否歧视、自残等内容
     *
     * @param moderation 文本审核参数
     * @return 审核分类结果
     */
    @POST("v1/moderations")
    Single<ModerationResponse> moderations(@Body ModerationRequest moderation);

    /**
     * 引擎列表，OPENAI已废弃该接口（Please use their replacement, Models, instead.）
     *
     * @return 引擎列表
     */
    @Deprecated
    @GET("v1/engines")
    Single<CommonListResponse<EngineItem>> engines();

    /**
     * 检索引擎，OPENAI已废弃该接口（Please use their replacement, Models, instead.）
     *
     * @param engineId 引擎ID
     * @return 引擎
     */
    @Deprecated
    @GET("v1/engines/{engine_id}")
    Single<EngineItem> engine(@Path("engine_id") String engineId);


}
