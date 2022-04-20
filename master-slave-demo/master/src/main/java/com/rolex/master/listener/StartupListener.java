package com.rolex.master.listener;

import com.rolex.discovery.broadcast.BroadcastService;
import com.rolex.discovery.routing.RouteCache;
import com.rolex.discovery.routing.RouteInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
@Slf4j
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private BroadcastService broadcastService;
    @Value("${tcp.port}")
    int port;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new BroadcastThread().start();
        new RoutingCheckThread().start();

    }

    class BroadcastThread extends Thread {

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                broadcastService.broadcast();
                Thread.sleep(10000);
            }
        }
    }

    class RoutingCheckThread extends Thread {

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                Map<String, Map<Integer, RouteInfo>> routeInfo = RouteCache.getRouteInfo();
                for (Map.Entry<String, Map<Integer, RouteInfo>> kv : routeInfo.entrySet()) {
                    String type = kv.getKey();
                    Map<Integer, RouteInfo> map = kv.getValue();
                    if("server".equals(type)) {
                        for (Map.Entry<Integer, RouteInfo> skv : map.entrySet()) {
                            Integer nodeId = skv.getKey();
                            long timestamp = skv.getValue().getTimestamp();
                            if (timestamp < System.currentTimeMillis() - 3000 * 2) {
                                routeInfo.remove(nodeId);
                            }
                        }
                    }
                    if("client".equals(type)) {
                        for (Map.Entry<Integer, RouteInfo> skv : map.entrySet()) {
                            Integer nodeId = skv.getKey();
                            long timestamp = skv.getValue().getTimestamp();
                            if (timestamp < System.currentTimeMillis() - 3000 * 2) {
                                routeInfo.remove(nodeId);
                            }
                        }
                    }
                }
                Thread.sleep(1000);
            }
        }
    }
}
