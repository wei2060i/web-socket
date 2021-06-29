package com.websocket.config;

import com.websocket.constant.ApplicationConstants;
import com.websocket.dto.RedisMsg;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * RedisPublishConfig
 *
 * @author Wei
 * @date 2020-12-23 11:14
 */
@Configuration
public class RedisPublishConfig {
    /**
     * redis消息监听器容器
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 可以添加多个 messageListener,配置不同的交换机
        container.addMessageListener(listenerAdapter, new PatternTopic(ApplicationConstants.WEBSOCKET_CHANNEL));
        return container;
    }

    /**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     * @param redisMsg
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(RedisMsg redisMsg) {

        return new MessageListenerAdapter(redisMsg, "receiveMessage");
    }

}
