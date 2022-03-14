package com.rolex.slave.service.impl;

import com.rolex.slave.service.PublisherService;
import com.rolex.slave.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class PublisherServiceImpl implements PublisherService {
    @Autowired
    private RedisService redisService;

    @Override
    public String pushMsg(String params) {
        redisService.sendChannelMessage("test_channel", params);
        return "success";
    }
}
