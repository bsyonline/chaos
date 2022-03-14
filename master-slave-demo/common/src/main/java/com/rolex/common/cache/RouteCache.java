package com.rolex.common.cache;

import com.google.common.collect.Maps;
import com.rolex.common.model.RouteInfo;
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
public class RouteCache {
    // TODO: 2022/3/7 lru cache
    public static Map<String, Map<Integer, RouteInfo>> routeInfoCache = Maps.newConcurrentMap();
    public static String connected = null;

    public static String getConnected() {
        return connected;
    }

    public static void setConnected(String connected) {
        RouteCache.connected = connected;
    }

    public static Map<String, Map<Integer, RouteInfo>> getRouteInfo() {
        return routeInfoCache;
    }

    public static void addRegistry(RouteInfo routeInfo) {
        if (routeInfoCache.get(routeInfo.getType()) == null) {
            HashMap<Integer, RouteInfo> map = Maps.newHashMap();
            map.put(routeInfo.getNodeId(), routeInfo);
            routeInfoCache.put(routeInfo.getType(), map);
        } else {
            routeInfoCache.get(routeInfo.getType()).put(routeInfo.getNodeId(), routeInfo);
        }
        log.info("路由信息为：{}", routeInfoCache);
    }
}
