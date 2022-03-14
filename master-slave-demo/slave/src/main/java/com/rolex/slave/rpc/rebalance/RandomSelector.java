package com.rolex.slave.rpc.rebalance;

import com.rolex.common.cache.RouteCache;
import com.rolex.common.model.RouteInfo;
import com.rolex.slave.model.ServerInfo;
import com.rolex.slave.rpc.ServerSelectorStrategy;
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
public class RandomSelector implements ServerSelectorStrategy {

    @Override
    public ServerInfo selectServer() {
        log.info("random select");
        Map<Integer, RouteInfo> server = RouteCache.getRouteInfo().get("server");
        Set<Integer> serverNodeIds = server.keySet();
        int index = 1;
        RouteInfo routeInfo = server.get(index);
        return new ServerInfo(routeInfo.getIp(), routeInfo.getPort());
    }
}
