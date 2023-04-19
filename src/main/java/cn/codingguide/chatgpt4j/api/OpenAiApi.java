package cn.codingguide.chatgpt4j.api;

import java.util.Map;

import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionRequest;
import cn.codingguide.chatgpt4j.domain.chat.ChatCompletionResponse;
import cn.codingguide.chatgpt4j.domain.completions.CompletionRequest;
import cn.codingguide.chatgpt4j.domain.completions.CompletionResponse;
import cn.codingguide.chatgpt4j.domain.edit.EditRequest;
import cn.codingguide.chatgpt4j.domain.edit.EditResponse;
import cn.codingguide.chatgpt4j.domain.images.ImageGenerationRequest;
import cn.codingguide.chatgpt4j.domain.images.ImageResponse;
import cn.codingguide.chatgpt4j.domain.models.Model;
import cn.codingguide.chatgpt4j.domain.models.ModelResponse;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
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
    Single<ModelResponse> models();

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
     * @param image 图片
     * @param mask 遮罩层
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
     * @param image 图片
     * @param requestBodyMap 请求参数
     * @return ImageResponse
     */
    @Multipart
    @POST("v1/images/variations")
    Single<ImageResponse> imageVariations(
            @Part() MultipartBody.Part image,
            @PartMap() Map<String, RequestBody> requestBodyMap);


}
