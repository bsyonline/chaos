package com.rolex.master.manager.loadbalance.strategy;

import com.rolex.discovery.routing.Host;
import com.rolex.master.manager.ExecutorManager;
import com.rolex.master.manager.loadbalance.LoadBalanceStrategy;
import io.netty.channel.Channel;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class RandomLoadBalance implements LoadBalanceStrategy {

    @Override
    public Host select(Collection<Host> hosts) {
        return null;
    }
}
