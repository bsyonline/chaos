package com.rolex.rpc.rebalance.strategy;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.rpc.rebalance.AbstractRebalanceStrategy;

import java.util.Collection;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class GroupRebalanceStrategy extends AbstractRebalanceStrategy {
    @Override
    protected Host doSelect(Collection<RoutingInfo> routings) {
        return null;
    }
}
