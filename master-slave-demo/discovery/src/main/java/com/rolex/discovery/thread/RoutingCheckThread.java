package com.rolex.discovery.thread;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.discovery.util.Constants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class RoutingCheckThread extends Thread {
    int count = 0;
    RoutingCache routingCache;

    public RoutingCheckThread(RoutingCache routingCache) {
        this.routingCache = routingCache;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
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
            if (!prepareCleanClient.isEmpty()) {
                for (Host host : prepareCleanClient) {
                    registry.get(NodeType.client).get(host).routingChange();
                    registry.get(NodeType.client).remove(host);
                }

            }
            if (!prepareCleanServer.isEmpty()) {
                for (Host host : prepareCleanClient) {
                    registry.get(NodeType.server).get(host).routingChange();
                    registry.get(NodeType.server).remove(host);
                }

            }
            Thread.sleep(1000);
        }
    }

    public long maxBroadcastTime() {
        return Constants.BROADCAST_TIME_MILLIS * 3 + 1000;
    }
}