package com.rolex.rpc.loadbalance;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.RoutingInfo;

import java.util.Collection;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface LoadBalanceStrategy {
    Host select(Collection<RoutingInfo> routings);
}
