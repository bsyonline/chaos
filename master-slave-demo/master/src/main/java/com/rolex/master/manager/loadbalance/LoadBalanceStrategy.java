package com.rolex.master.manager.loadbalance;

import com.rolex.discovery.routing.Host;

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
    Host select(Collection<Host> hosts);
}
