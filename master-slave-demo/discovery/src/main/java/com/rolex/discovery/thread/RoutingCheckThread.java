package com.rolex.discovery.thread;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.discovery.util.Constants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class RoutingCheckThread extends Thread {
    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Map<NodeType, Map<Host, RoutingInfo>> registry = RoutingCache.getRoutingInfo();
            log.info("路由信息为：{}", registry);
            log.info("connect信息为：{}", RoutingCache.getConnects());
            for (Map.Entry<NodeType, Map<Host, RoutingInfo>> kv : registry.entrySet()) {
                NodeType type = kv.getKey();
                Map<Host, RoutingInfo> map = kv.getValue();
                switch (type){
                    case server:
                        for (Map.Entry<Host, RoutingInfo> skv : map.entrySet()) {
                            Host host = skv.getKey();
                            long timestamp = skv.getValue().getTimestamp();
                            if (timestamp < System.currentTimeMillis() - maxBroadcastTime()) {
                                registry.get(NodeType.server).remove(host);
                                skv.getValue().routingChange();
                            }
                        }
                        break;
                    case client:
                        for (Map.Entry<Host, RoutingInfo> skv : map.entrySet()) {
                            Host host = skv.getKey();
                            long timestamp = skv.getValue().getTimestamp();
                            if (timestamp < System.currentTimeMillis() - maxBroadcastTime()) {
                                registry.get(NodeType.client).remove(host);
                                skv.getValue().routingChange();
                            }
                        }
                        break;
                }
            }
            Thread.sleep(1000);
        }
    }

    public long maxBroadcastTime() {
        return Constants.BROADCAST_TIME_MILLIS * 3 + 1000;
    }
}