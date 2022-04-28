package com.rolex.master.listener;

import com.rolex.discovery.broadcast.BroadcastService;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.thread.BroadcastThread;
import com.rolex.discovery.thread.RoutingCheckThread;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
@ConditionalOnClass(RoutingCache.class)
@Slf4j
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    RoutingCache routingCache;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new BroadcastThread(broadcastService).start();
        new RoutingCheckThread(applicationEventPublisher, routingCache).start();
    }

}
