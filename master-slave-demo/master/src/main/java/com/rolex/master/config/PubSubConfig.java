package com.rolex.master.config;

import com.rolex.master.service.SubService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Configuration
public class PubSubConfig {
    /**
     * 初始化监听器
     *
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("test_channel"));
        return container;
    }

    /**
     * 绑定消息监听者和接收监听的方法
     *
     * @param subService
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(SubService subService) {
        // redisReceiver 消息接收者
        // receiveMessage 消息接收后的方法
        return new MessageListenerAdapter(subService, "receiveMessage");
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

}
