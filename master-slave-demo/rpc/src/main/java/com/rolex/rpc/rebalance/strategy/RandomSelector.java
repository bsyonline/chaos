package com.rolex.rpc.rebalance.strategy;

import com.rolex.discovery.routing.RouteCache;
import com.rolex.discovery.routing.RouteInfo;
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
        Map<Integer, RouteInfo> server = RouteCache.getRouteInfo().get("server");
        Set<Integer> serverNodeIds = server.keySet();
        int index = 1;
        RouteInfo routeInfo = server.get(index);
        return new ServerInfo(routeInfo.getIp(), routeInfo.getPort());
    }
}
