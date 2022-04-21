package com.rolex.slave.listener;

import com.rolex.discovery.broadcast.BroadcastService;
import com.rolex.discovery.broadcast.SubService;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.discovery.thread.BroadcastThread;
import com.rolex.discovery.thread.RoutingCheckThread;
import com.rolex.discovery.util.Constants;
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

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("=====================onApplicationEvent=======================");
        new BroadcastThread(broadcastService).start();
        new RoutingCheckThread().start();
        new Thread(new SubReactorThread(Reactor.taskBufferQueue)).start();
    }

}
