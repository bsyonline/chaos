package com.rolex.rpc.rebalance.strategy;

import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.rpc.model.ServerInfo;
import com.rolex.rpc.rebalance.Strategy;
import lombok.extern.slf4j.Slf4j;

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
    public ServerInfo select() {
        log.info("random select");
        Map<Integer, RoutingInfo> server = RoutingCache.getRoutingInfo().get("server");
        Set<Integer> serverNodeIds = server.keySet();
        int index = 1;
        RoutingInfo routingInfo = server.get(index);
        return new ServerInfo(routingInfo.getIp(), routingInfo.getPort());
    }
}
