package com.rolex.discovery.routing;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
@Component
public class RoutingCache {
    // TODO: 2022/3/7 lru cache
    public Map<NodeType, Map<Host, RoutingInfo>> routingInfoCache = Maps.newConcurrentMap();
    public Map<String, Object> localRoutingInfo = Maps.newConcurrentMap();
    public Map<Host, Channel> connectMap = Maps.newConcurrentMap();

    public Map<String, Object> getLocalRoutingInfo() {
        return localRoutingInfo;
    }

    public void setLocalRoutingInfo(String key, Object val) {
        localRoutingInfo.put(key, val);
    }

    public void clearLocalRoutingInfo() {
        localRoutingInfo = Maps.newConcurrentMap();
    }

    public Map<Host, Channel> getConnects() {
        return connectMap;
    }

    public void removeConnect(Host host) {
        connectMap.remove(host);
    }

    public void addConnect(Host host, Channel channel) {
        connectMap.put(host, channel);
    }

    public Map<NodeType, Map<Host, RoutingInfo>> getRoutingInfo() {
        return routingInfoCache;
    }

    public void addRegistry(RoutingInfo routingInfo) {
        if (routingInfo.getHost().getPort() == 0) {
            return;
        }
        Map<Host, RoutingInfo> routingInfoMap = routingInfoCache.get(routingInfo.getType());
        if (routingInfoMap == null) {
            HashMap<Host, RoutingInfo> map = Maps.newHashMap();
            map.put(routingInfo.getHost(), routingInfo);
            routingInfoCache.put(routingInfo.getType(), map);
        } else {
            routingInfoMap.put(routingInfo.getHost(), routingInfo);
        }
    }


    public String getHost() {
        return (String) localRoutingInfo.get("host");
    }

    public Object getPort() {
        return localRoutingInfo.get("port");
    }
}
