package com.rolex.rpc.rebalance.strategy;

import com.rolex.discovery.routing.Host;
import com.rolex.rpc.rebalance.Strategy;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class ExcludeSelector implements Strategy {
    @Override
    public Host select() {
        return null;
    }
}
