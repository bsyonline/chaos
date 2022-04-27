package com.rolex.rpc.loadbalance;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class MasterLoadBalancer implements LoadBalancer {
    LoadBalanceStrategy loadBalanceStrategy;
    RoutingCache routingCache;

    public MasterLoadBalancer(RoutingCache routingCache, LoadBalanceStrategy loadBalanceStrategy) {
        this.loadBalanceStrategy = loadBalanceStrategy;
        this.routingCache = routingCache;
    }

    @Override
    public Channel select() {
        Map<Host, Channel> channelMap = routingCache.getConnects();
        if (channelMap == null) {
            return null;
        }
        List<RoutingInfo> routings = new ArrayList<>();
        for (Host host : channelMap.keySet()) {
            routings.add(routingCache.getRoutingInfo().get(NodeType.client).get(host));
        }

        Host host = loadBalanceStrategy.select(routings);
        if (host == null) {
            return null;
        }
        Channel channel = channelMap.get(host);
        if (channel == null) {
            return null;
        }
        if (!channel.isActive()) {
            return null;
        }
        return channel;
    }
}
