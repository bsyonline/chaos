package com.rolex.rpc.rebalance;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.rpc.loadbalance.LoadBalanceStrategy;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public abstract class AbstractRebalanceStrategy implements RebalanceStrategy {
    @Override
    public Host select(Collection<RoutingInfo> routings) {
        if (CollectionUtils.isEmpty(routings)) {
            return null;
        }
        if (routings.size() == 1) {
            return ((RoutingInfo) routings.toArray()[0]).getHost();
        }
        return doSelect(routings);
    }

    protected abstract Host doSelect(Collection<RoutingInfo> routings);
}
