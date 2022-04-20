package com.rolex.discovery.config;

import com.rolex.discovery.broadcast.SubService;
import com.rolex.discovery.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
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
        container.addMessageListener(listenerAdapter, new PatternTopic(Constants.ROUTE_INFO_TOPIC));
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
        return new MessageListenerAdapter(subService, "sub");
    }

}
