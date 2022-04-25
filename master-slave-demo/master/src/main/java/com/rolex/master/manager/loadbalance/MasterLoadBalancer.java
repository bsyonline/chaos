package com.rolex.master.manager.loadbalance;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.RoutingCache;
import io.netty.channel.Channel;

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

        Host host = loadBalanceStrategy.select(channelMap.keySet());
        return channelMap.get(host);
    }
}
