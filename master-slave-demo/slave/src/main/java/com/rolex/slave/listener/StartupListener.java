package com.rolex.slave.listener;

import com.rolex.discovery.broadcast.BroadcastService;
import com.rolex.discovery.broadcast.SubService;
import com.rolex.discovery.routing.RouteCache;
import com.rolex.discovery.routing.RouteInfo;
import com.rolex.slave.exec.Reactor;
import com.rolex.slave.exec.SubReactorThread;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Autowired
    SubService subService;
    @Autowired
    BroadcastService broadcastService;

    ExecutorService subReactorThreadPool = Executors.newFixedThreadPool(5);


    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("=====================onApplicationEvent=======================");
        new BroadcastThread().start();
        new RegistryInfoCheckThread().start();
        new Thread(new SubReactorThread(Reactor.taskBufferQueue)).start();
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

    class RegistryInfoCheckThread extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                Map<String, Map<Integer, RouteInfo>> registry = RouteCache.getRouteInfo();
                for (Map.Entry<String, Map<Integer, RouteInfo>> kv : registry.entrySet()) {
                    String type = kv.getKey();
                    Map<Integer, RouteInfo> map = kv.getValue();
                    if ("server".equals(type)) {
                        for (Map.Entry<Integer, RouteInfo> skv : map.entrySet()) {
                            Integer nodeId = skv.getKey();
                            long timestamp = skv.getValue().getTimestamp();
                            if (timestamp < System.currentTimeMillis() - 3000 * 2) {
                                registry.remove(nodeId);
                            }
                        }
                    }
                    if ("client".equals(type)) {
                        for (Map.Entry<Integer, RouteInfo> skv : map.entrySet()) {
                            Integer nodeId = skv.getKey();
                            long timestamp = skv.getValue().getTimestamp();
                            if (timestamp < System.currentTimeMillis() - 3000 * 2) {
                                registry.remove(nodeId);
                            }
                        }
                    }
                }
                Thread.sleep(1000);
            }
        }
    }
}
