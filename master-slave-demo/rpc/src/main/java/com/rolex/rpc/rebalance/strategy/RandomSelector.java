package com.rolex.rpc.rebalance.strategy;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.rpc.rebalance.Strategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class RandomSelector implements Strategy {

    @Override
    public Host select(List<Host> servers) {
        log.info("random select");
        Host host = null;
        Iterator<Host> iterator = servers.iterator();
        while (iterator.hasNext()) {
            host = iterator.next();
        }
        return host;
    }
}
