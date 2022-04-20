/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.rpc;

import com.rolex.discovery.routing.RouteCache;
import com.rolex.discovery.routing.RouteInfo;
import com.rolex.rpc.codec.MsgDecoder;
import com.rolex.rpc.codec.MsgEncoder;
import com.rolex.rpc.handler.NettyClientHandler;
import com.rolex.rpc.model.ServerInfo;
import com.rolex.rpc.processor.NettyRequestProcessor;
import com.rolex.rpc.rebalance.Strategy;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author rolex
 * @since 2019
 */
@Slf4j
public class NettyClient {
    private final ExecutorService defaultExecutor = Executors.newFixedThreadPool(5);
    volatile boolean connected = false;
    private Strategy serverSelectorStrategy;
    private final NettyClientHandler clientHandler = new NettyClientHandler(this);

    public ExecutorService getDefaultExecutor() {
        return defaultExecutor;
    }

    public void setServerSelectorStrategy(Strategy serverSelectorStrategy) {
        this.serverSelectorStrategy = serverSelectorStrategy;
    }


    /**
     * register processor
     *
     * @param commandType command type
     * @param processor   processor
     */
    public void registerProcessor(final CommandType commandType, final NettyRequestProcessor processor) {
        this.registerProcessor(commandType, processor, null);
    }

    /**
     * register processor
     *
     * @param commandType command type
     * @param processor   processor
     * @param executor    thread executor
     */
    public void registerProcessor(final CommandType commandType, final NettyRequestProcessor processor, final ExecutorService executor) {
        this.clientHandler.registerProcessor(commandType, processor, executor);
    }

    public void start() throws InterruptedException {
        while (!connected) {
            Map<String, Map<Integer, RouteInfo>> registry = RouteCache.getRouteInfo();
            Map<Integer, RouteInfo> serverInfo = registry.get("server");
            if (serverInfo == null || serverInfo.isEmpty()) {
                log.info("等待master上线");
                Thread.sleep(3000);
            } else {
                connected = true;
                log.info("发现server的注册信息：{}", serverInfo);
            }
        }
        connect();
    }

    private void connect() throws InterruptedException {
        ServerInfo serverInfo = serverSelectorStrategy.select();
        log.info("starting connect to server {}", serverInfo);
        start(serverInfo);
    }

    public void start(ServerInfo serverInfo) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(serverInfo.getHost(), serverInfo.getPort()))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("idle-state-handler", new IdleStateHandler(5, 0, 0));
                        pipeline.addLast("decoder", new MsgDecoder());
                        pipeline.addLast("encoder", new MsgEncoder());
                        pipeline.addLast("client-handler", clientHandler);
                    }
                });
        ChannelFuture future = bootstrap.connect().sync();
        future.channel().closeFuture().sync();
    }
}
