/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.rpc;

import com.rolex.discovery.routing.RoutingCache;
import com.rolex.rpc.handler.ProtoNettyClientHandler;
import com.rolex.rpc.manager.ConnectionManager;
import com.rolex.rpc.model.proto.MsgProto;
import com.rolex.rpc.processor.NettyProcessor;
import com.rolex.rpc.rebalance.RebalanceStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

/**
 * @author rolex
 * @since 2019
 */
@Slf4j
public class NettyClient {
    private final ProtoNettyClientHandler clientHandler = new ProtoNettyClientHandler();
//    private final NettyClientHandler clientHandler = new NettyClientHandler();

    public void setHttpPort(int httpPort) {
        this.clientHandler.setHttpPort(httpPort);
    }

    public void setExecutorType(String executorType) {
        this.clientHandler.setExecutorType(executorType);
    }

    public void setServerSelectorStrategy(RebalanceStrategy serverSelectorRebalanceStrategy) {
        this.clientHandler.setServerSelectorStrategy(serverSelectorRebalanceStrategy);
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
    public void registerProcessor(final MsgProto.CommandType commandType, final NettyProcessor processor) {
        this.registerProcessor(commandType, processor, null);
    }

    /**
     * register processor
     *
     * @param commandType command type
     * @param processor   processor
     * @param executor    thread executor
     */
    public void registerProcessor(final MsgProto.CommandType commandType, final NettyProcessor processor, final ExecutorService executor) {
        this.clientHandler.registerProcessor(commandType, processor, executor);
    }

    public void start() throws InterruptedException {
        new ConnectionManager(clientHandler).startConnect();
    }

}
