package com.websocket;

import com.websocket.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

@SpringBootTest
public class WebSocketApplicationTests {

    /**
     * 测试和ServerEndpointExporter冲突
     * 去掉 @Bean
     */
    @Test
    public void contextLoads() {
        System.out.println(RedisUtils.exists("6"));
    }

}
