package com.rolex.discovery.thread;

import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.discovery.util.Constants;
import lombok.SneakyThrows;

import java.util.Map;

public class RoutingCheckThread extends Thread {
    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Map<NodeType, Map<Integer, RoutingInfo>> registry = RoutingCache.getRoutingInfo();
            for (Map.Entry<NodeType, Map<Integer, RoutingInfo>> kv : registry.entrySet()) {
                NodeType type = kv.getKey();
                Map<Integer, RoutingInfo> map = kv.getValue();
                if (NodeType.server == type) {
                    for (Map.Entry<Integer, RoutingInfo> skv : map.entrySet()) {
                        Integer nodeId = skv.getKey();
                        long timestamp = skv.getValue().getTimestamp();
                        if (timestamp < System.currentTimeMillis() - maxBroadcastTime()) {
                            registry.get("server").remove(nodeId);
                            skv.getValue().routingChange();
                        }
                    }
                }
                if (NodeType.client == type) {
                    for (Map.Entry<Integer, RoutingInfo> skv : map.entrySet()) {
                        Integer nodeId = skv.getKey();
                        long timestamp = skv.getValue().getTimestamp();
                        if (timestamp < System.currentTimeMillis() - maxBroadcastTime()) {
                            registry.get("client").remove(nodeId);
                            skv.getValue().routingChange();
                        }
                    }
                }
            }
            Thread.sleep(1000);
        }
    }

    public long maxBroadcastTime() {
        return Constants.BROADCAST_TIME_MILLIS * 3 + 1000;
    }
}