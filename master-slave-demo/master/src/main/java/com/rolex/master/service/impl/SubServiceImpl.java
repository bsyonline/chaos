package com.rolex.master.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rolex.common.cache.RouteCache;
import com.rolex.common.model.RouteInfo;
import com.rolex.master.service.SubService;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public void receiveMessage(String message) {
        log.info("收到广播消息：{}", message);
        RouteInfo routeInfo = JSONObject.parseObject(message, RouteInfo.class);
        RouteCache.addRegistry(routeInfo);
    }

}