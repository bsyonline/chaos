package com.rolex.rpc.loadbalance.strategy;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.rpc.loadbalance.AbstractLoadBalanceStrategy;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class RandomLoadBalanceStrategy extends AbstractLoadBalanceStrategy {
    @Override
    protected Host doSelect(Collection<RoutingInfo> routings) {
        log.info("use randomLoadBalanceStrategy");
        List<RoutingInfo> routingList = new ArrayList<>(routings);
        routingList.sort((o1, o2) -> (o1.getHost().getHost() + o1.getHost().getPort()).compareTo(o2.getHost().getHost() + o2.getHost().getPort()));
        return routingList.get(ThreadLocalRandom.current().nextInt(routingList.size())).getHost();
    }
}
