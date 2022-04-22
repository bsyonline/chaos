package com.rolex.discovery.routing;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rolex.discovery.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    public static Map<NodeType, Map<Host, RoutingInfo>> routingInfoCache = Maps.newConcurrentMap();
    /*
        <ip:port, ts>
     */
    public static Set<Host> connectSet = Sets.newConcurrentHashSet();

    public static Set<Host> getConnects() {
        return connectSet;
    }

    public static void removeConnect(Host host) {
        RoutingCache.connectSet.remove(host);
    }

    public static void addConnect(Host host) {
        RoutingCache.connectSet.add(host);
    }

    public static Map<NodeType, Map<Host, RoutingInfo>> getRoutingInfo() {
        return routingInfoCache;
    }

    public static void addRegistry(RoutingInfo routingInfo) {
        Map<Host, RoutingInfo> routingInfoMap = routingInfoCache.get(routingInfo.getType());
        if (routingInfoMap == null) {
            HashMap<Host, RoutingInfo> map = Maps.newHashMap();
            map.put(routingInfo.getHost(), routingInfo);
            routingInfoCache.put(routingInfo.getType(), map);
        } else {
            routingInfoMap.put(routingInfo.getHost(), routingInfo);
        }
    }

}
