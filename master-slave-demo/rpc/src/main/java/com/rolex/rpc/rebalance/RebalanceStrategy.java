package com.rolex.rpc.rebalance;

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
public interface RebalanceStrategy {
    Host select(Collection<RoutingInfo> routings);
}
