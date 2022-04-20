package com.rolex.discovery.broadcast.impl;

import com.rolex.discovery.broadcast.PubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.rolex.discovery.util.Constants.ROUTE_INFO_TOPIC;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
@Service
public class PubServiceImpl implements PubService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String pub(String message) {
        stringRedisTemplate.convertAndSend(ROUTE_INFO_TOPIC, message);
        return "success";
    }
}
