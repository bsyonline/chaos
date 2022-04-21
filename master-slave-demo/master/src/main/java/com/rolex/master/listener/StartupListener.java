package com.rolex.master.listener;

import com.rolex.discovery.broadcast.BroadcastService;
import com.rolex.discovery.thread.BroadcastThread;
import com.rolex.discovery.thread.RoutingCheckThread;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new BroadcastThread(broadcastService).start();
        new RoutingCheckThread().start();
    }

}
