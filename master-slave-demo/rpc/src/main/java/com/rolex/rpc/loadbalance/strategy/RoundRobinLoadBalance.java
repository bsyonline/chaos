package com.rolex.rpc.loadbalance.strategy;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.rpc.loadbalance.LoadBalanceStrategy;

import java.util.Collection;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class RoundRobinLoadBalance implements LoadBalanceStrategy {
    @Override
    public Host select(Collection<RoutingInfo> routings) {
        return null;
    }
}
