/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.rpc;

import com.rolex.discovery.routing.RoutingCache;
import com.rolex.rpc.handler.NettyClientHandler;
import com.rolex.rpc.manager.ConnectionManager;
import com.rolex.rpc.processor.NettyProcessor;
import com.rolex.rpc.rebalance.Strategy;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

/**
 * @author rolex
 * @since 2019
 */
@Slf4j
public class NettyClient {
    private final NettyClientHandler clientHandler = new NettyClientHandler();

    public void setServerSelectorStrategy(Strategy serverSelectorStrategy) {
        this.clientHandler.setServerSelectorStrategy(serverSelectorStrategy);
    }

    public void setRoutingCache(RoutingCache routingCache) {
        this.clientHandler.setRoutingCache(routingCache);
    }

    /**
     * register processor
     *
     * @param commandType command type
     * @param processor   processor
     */
    public void registerProcessor(final CommandType commandType, final NettyProcessor processor) {
        this.registerProcessor(commandType, processor, null);
    }

    /**
     * register processor
     *
     * @param commandType command type
     * @param processor   processor
     * @param executor    thread executor
     */
    public void registerProcessor(final CommandType commandType, final NettyProcessor processor, final ExecutorService executor) {
        this.clientHandler.registerProcessor(commandType, processor, executor);
    }

    public void start() throws InterruptedException {
        new ConnectionManager(clientHandler).startConnect();
    }

}
