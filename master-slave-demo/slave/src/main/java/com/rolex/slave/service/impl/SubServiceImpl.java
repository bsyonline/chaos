package com.rolex.slave.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rolex.common.cache.RouteCache;
import com.rolex.common.model.RouteInfo;
import com.rolex.slave.service.SubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    public static Map<String, RouteInfo> registry = new ConcurrentHashMap<>();

    @Override
    public void receiveMessage(String message) {
        log.info("收到广播消息：{}", message);
        RouteInfo routeInfo = JSONObject.parseObject(message, RouteInfo.class);
        RouteCache.addRegistry(routeInfo);
    }

    @Override
    public Map<String, RouteInfo> getRegistry() {
        return registry;
    }
}