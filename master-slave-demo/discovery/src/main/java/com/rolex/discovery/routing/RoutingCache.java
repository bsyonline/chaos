package com.rolex.discovery.routing;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class RoutingCache {
    // TODO: 2022/3/7 lru cache
    public static Map<NodeType, Map<Integer, RoutingInfo>> routingInfoCache = Maps.newConcurrentMap();
    public static String connected = null;

    public static String getConnected() {
        return connected;
    }

    public static void setConnected(String connected) {
        RoutingCache.connected = connected;
    }

    public static Map<NodeType, Map<Integer, RoutingInfo>> getRoutingInfo() {
        return routingInfoCache;
    }

    public static void addRegistry(RoutingInfo routingInfo) {
        if (routingInfoCache.get(routingInfo.getType()) == null) {
            HashMap<Integer, RoutingInfo> map = Maps.newHashMap();
            map.put(routingInfo.getNodeId(), routingInfo);
            routingInfoCache.put(routingInfo.getType(), map);
        } else {
            routingInfoCache.get(routingInfo.getType()).put(routingInfo.getNodeId(), routingInfo);
        }
        log.info("路由信息为：{}", routingInfoCache);
    }
}
