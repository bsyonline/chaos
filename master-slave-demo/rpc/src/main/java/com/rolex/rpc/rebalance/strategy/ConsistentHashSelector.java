package com.rolex.rpc.rebalance.strategy;

import com.rolex.discovery.routing.Host;
import com.rolex.rpc.rebalance.Strategy;

import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class ConsistentHashSelector implements Strategy {
    @Override
    public Host select(List<Host> servers) {
        return null;
    }
}
