package cn.codingguide.chatgpt4j.api;

import cn.codingguide.chatgpt4j.model.ModelResponse;
import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-04
 */
public interface OpenAiApi {

    String OPEN_AI_HOST = "https://api.openai.com/";

    /**
     * 获取Open AI模型列表
     *
     * @return Single ModelResponse
     */
    @GET("v1/models")
    Single<ModelResponse> models();


}
