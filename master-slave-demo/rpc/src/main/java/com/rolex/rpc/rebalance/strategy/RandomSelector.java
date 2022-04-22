package com.rolex.rpc.rebalance.strategy;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.rpc.rebalance.Strategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
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
    public Host select() {
        log.info("random select");
        Map<Host, RoutingInfo> server = RoutingCache.getRoutingInfo().get(NodeType.server);
        Set<Host> hosts = server.keySet();
        Host host = null;
        Iterator<Host> iterator = hosts.iterator();
        while (iterator.hasNext()) {
            host = iterator.next();
        }
        return host;
    }
}
