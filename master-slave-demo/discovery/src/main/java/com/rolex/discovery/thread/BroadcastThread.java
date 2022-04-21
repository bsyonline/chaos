package com.rolex.discovery.thread;

import com.rolex.discovery.broadcast.BroadcastService;
import lombok.SneakyThrows;

public class BroadcastThread extends Thread {
    BroadcastService broadcastService;

    public BroadcastThread(BroadcastService broadcastService) {
        this.broadcastService = broadcastService;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            broadcastService.broadcast();
            Thread.sleep(10000);
        }
    }
}