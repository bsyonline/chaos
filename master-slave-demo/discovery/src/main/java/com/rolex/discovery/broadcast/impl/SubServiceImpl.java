package com.rolex.discovery.broadcast.impl;

import com.alibaba.fastjson.JSONObject;
import com.rolex.discovery.broadcast.SubService;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
@Component
public class SubServiceImpl implements SubService {

    @Autowired
    RoutingCache routingCache;

    @Override
    public void sub(String message) {
        log.info("收到广播消息：{}", message);
        RoutingInfo routingInfo = JSONObject.parseObject(message, RoutingInfo.class);
        routingCache.addRegistry(routingInfo);
    }

}