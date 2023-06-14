package com.rolex.discovery.thread;

import com.rolex.discovery.observer.RoutingInfoObserver;
import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.discovery.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class RoutingCheckThread extends Thread {
    int count = 0;
    RoutingCache routingCache;
    ApplicationEventPublisher applicationEventPublisher;

    public RoutingCheckThread(ApplicationEventPublisher applicationEventPublisher, RoutingCache routingCache) {
        this.routingCache = routingCache;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Map<NodeType, Map<Host, RoutingInfo>> registry = routingCache.getRoutingInfo();
                if (count % 10 == 0) {
                    log.info("{} 路由信息为：{}", routingCache.getRoutingInfo().hashCode(), registry);
                    log.info("connect信息为：{}", routingCache.getConnects());
                    count = 0;
                }
                count++;

                Set<Host> prepareCleanClient = new HashSet<>();
                Set<Host> prepareCleanServer = new HashSet<>();
                for (Map.Entry<NodeType, Map<Host, RoutingInfo>> kv : registry.entrySet()) {
                    NodeType type = kv.getKey();
                    Map<Host, RoutingInfo> map = kv.getValue();
                    switch (type) {
                        case server:
                            for (Map.Entry<Host, RoutingInfo> skv : map.entrySet()) {
                                Host host = skv.getKey();
                                long timestamp = skv.getValue().getTimestamp();
                                if (timestamp < System.currentTimeMillis() - maxBroadcastTime()) {
                                    prepareCleanServer.add(host);
                                }
                            }
                            break;
                        case client:
                            for (Map.Entry<Host, RoutingInfo> skv : map.entrySet()) {
                                Host host = skv.getKey();
                                long timestamp = skv.getValue().getTimestamp();
                                if (timestamp < System.currentTimeMillis() - maxBroadcastTime()) {
                                    prepareCleanClient.add(host);
                                }
                            }
                            break;
                    }
                }
                List<RoutingInfo> removed = new ArrayList<>();
                if (!prepareCleanClient.isEmpty()) {
                    for (Host host : prepareCleanClient) {
                        RoutingInfo removedRoutingInfo = registry.get(NodeType.client).remove(host);
                        removed.add(removedRoutingInfo);
                    }
                }
                if (!prepareCleanServer.isEmpty()) {
                    for (Host host : prepareCleanServer) {
                        RoutingInfo removedRoutingInfo = registry.get(NodeType.server).remove(host);
                        removed.add(removedRoutingInfo);
                    }
                }
                if (!removed.isEmpty()) {
                    for (RoutingInfo routingInfo : removed) {
                        Map<Host, RoutingInfo> clientMap = registry.get(NodeType.client);
                        if (clientMap != null && !clientMap.isEmpty() && clientMap.values() != null && !clientMap.values().isEmpty()) {
                            clientMap.values().forEach(r -> r.routingChange(applicationEventPublisher, routingInfo));
                        }
                        Map<Host, RoutingInfo> serverMap = registry.get(NodeType.server);
                        if (serverMap != null && !serverMap.isEmpty() && serverMap.values() != null && !serverMap.values().isEmpty()) {
                            serverMap.values().forEach(r -> r.routingChange(applicationEventPublisher, routingInfo));
//                            RoutingInfo current = serverMap.get(Host.of((String) routingCache.getLocalRoutingInfo().get("host"), (int) routingCache.getLocalRoutingInfo().get("port")));
//                            ((RoutingInfoObserver)routingCache.getLocalRoutingInfo().get("observer")).onChange(applicationEventPublisher, current, routingInfo);
                        }
                        routingInfo.routingChange(applicationEventPublisher, routingInfo);
                    }
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                log.error("RoutingCheckThread process failed", e);
            }
        }
    }

    private List<RoutingInfo> clearCache(Map<NodeType, Map<Host, RoutingInfo>> registry, Set<Host> prepareCleanClient, NodeType client) {
        List<RoutingInfo> removed = new ArrayList<>();
        for (Host host : prepareCleanClient) {
            RoutingInfo removedRoutingInfo = registry.get(client).remove(host);
            removed.add(removedRoutingInfo);
        }
        return removed;
    }

    private long maxBroadcastTime() {
        return Constants.BROADCAST_TIME_MILLIS * 3 + 1000;
    }
}