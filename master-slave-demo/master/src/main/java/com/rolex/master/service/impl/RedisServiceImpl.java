package com.rolex.master.service.impl;

import com.rolex.master.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 向通道发送消息的方法
     *
     * @param channel
     * @param message
     */
    @Override
    public void sendChannelMess(String channel, String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }
}
