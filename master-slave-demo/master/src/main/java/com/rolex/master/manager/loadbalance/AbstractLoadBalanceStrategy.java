package com.rolex.master.manager.loadbalance;

import com.rolex.discovery.routing.Host;
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
public abstract class AbstractLoadBalanceStrategy implements LoadBalanceStrategy {
    @Override
    public Host select(Collection<Host> hosts) {
        if (CollectionUtils.isEmpty(hosts)) {
            throw new IllegalArgumentException("Empty source.");
        }
        if (hosts.size() == 1) {
            return (Host) hosts.toArray()[0];
        }
        return doSelect(hosts);
    }

    protected abstract Host doSelect(Collection<Host> hosts);
}
