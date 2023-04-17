package cn.codingguide.chatgpt4j.api;

import cn.codingguide.chatgpt4j.domain.completions.CompletionRequest;
import cn.codingguide.chatgpt4j.domain.completions.CompletionResponse;
import cn.codingguide.chatgpt4j.domain.models.Model;
import cn.codingguide.chatgpt4j.domain.models.ModelResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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


}
