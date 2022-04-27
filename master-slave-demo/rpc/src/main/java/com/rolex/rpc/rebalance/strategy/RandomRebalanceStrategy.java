package com.rolex.rpc.rebalance.strategy;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.rpc.rebalance.AbstractRebalanceStrategy;
import com.rolex.rpc.rebalance.RebalanceStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
public class RandomRebalanceStrategy extends AbstractRebalanceStrategy {
    @Override
    protected Host doSelect(Collection<RoutingInfo> routings) {
        log.info("use randomRebalanceStrategy");
        List<RoutingInfo> routingList = new ArrayList<>(routings);
        routingList.sort((o1, o2) -> (o1.getConnected().size() - o2.getConnected().size()));
        return routingList.get(0).getHost();
    }
}
