package cn.codingguide.chatgpt4j.client;

import cn.codingguide.chatgpt4j.DefaultChatGptClient;
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
                .apiKeys(Arrays.asList("sk-*******"))
                .enableHttpDetailLog(true)
                .build();
    }

    @Test
    public void models() {
        List<Model> models = client.models();
        models.forEach(e -> {
            System.out.print(e.getOwnedBy() + " ");
            System.out.print(e.getId() + " ");
            System.out.println(e.getObject() + " ");
        });
    }

}