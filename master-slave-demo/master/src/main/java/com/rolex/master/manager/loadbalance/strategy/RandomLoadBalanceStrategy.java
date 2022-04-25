package com.rolex.master.manager.loadbalance.strategy;

import com.rolex.discovery.routing.Host;
import com.rolex.master.manager.loadbalance.AbstractLoadBalanceStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class RandomLoadBalanceStrategy extends AbstractLoadBalanceStrategy {
    @Override
    protected Host doSelect(Collection<Host> hosts) {
        List<Host> hostList = new ArrayList<>(hosts);
        hostList.sort((o1, o2) -> (o1.getHost() + o1.getPort()).compareTo(o2.getHost() + o2.getPort()));
        return hostList.get(ThreadLocalRandom.current().nextInt(hostList.size()));
    }
}
