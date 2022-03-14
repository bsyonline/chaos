package com.rolex.slave.listener;

import com.rolex.common.cache.RouteCache;
import com.rolex.common.model.RouteInfo;
import com.rolex.slave.client.NettyClient;
import com.rolex.slave.exec.SubReactor;
import com.rolex.slave.exec.SubReactorThread;
import com.rolex.slave.exec.WorkerThread;
import com.rolex.slave.service.BroadcastService;
import com.rolex.slave.service.SubService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
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
        new BroadcastThread().start();
        new RegistryInfoCheckThread().start();
//        workerThreadPool.submit(WorkerThread::new);
        subReactorThreadPool.submit(new SubReactorThread(new SubReactor()));
//        new Thread(new SubReactorThread(new SubReactor())).start();
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
