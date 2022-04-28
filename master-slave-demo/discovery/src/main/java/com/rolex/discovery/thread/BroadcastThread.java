package com.rolex.discovery.thread;

import com.rolex.discovery.broadcast.BroadcastService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BroadcastThread extends Thread {
    BroadcastService broadcastService;

    public BroadcastThread(BroadcastService broadcastService) {
        this.broadcastService = broadcastService;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            try {
                broadcastService.broadcast();
                Thread.sleep(10000);
            }catch (Exception e){
                log.error("broadcast failed", e);
            }
        }
    }
}