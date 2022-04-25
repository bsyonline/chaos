/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.rpc;

import com.rolex.discovery.routing.LocalRoutingInfo;
import com.rolex.discovery.routing.NodeState;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.rpc.codec.MsgDecoder;
import com.rolex.rpc.codec.MsgEncoder;
import com.rolex.rpc.handler.NettyServerHandler;
import com.rolex.rpc.model.Manager;
import com.rolex.rpc.processor.NettyProcessor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author rolex
 * @since 2019
 */
@Slf4j
public class NettyServer {

    private final ExecutorService defaultExecutor = Executors.newFixedThreadPool(5);
    String host;
    int port;
    private final NettyServerHandler serverHandler = new NettyServerHandler(this);
    Manager manager;

    public NettyServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void setRoutingCache(RoutingCache routingCache) {
        this.serverHandler.setRoutingCache(routingCache);
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
        this.serverHandler.registerProcessor(commandType, processor, executor);
    }

    public ExecutorService getDefaultExecutor() {
        return defaultExecutor;
    }

    public NettyServer executorManager(Manager manager) {
        this.manager = manager;
        serverHandler.setExecutorManager(manager);
        return this;
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("idle-state-handler", new IdleStateHandler(0, 0, 16));
                            pipeline.addLast("decoder", new MsgDecoder());
                            pipeline.addLast("encoder", new MsgEncoder());
                            pipeline.addLast("server-handler", serverHandler);
                        }
                    })
                    .localAddress(host, port);

            ChannelFuture future = bootstrap.bind().sync();
            log.info("server started on port {}", port);
            future.channel().closeFuture();
//            setServerLocalRoutingInfo();
        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
        }
    }

    private void setServerLocalRoutingInfo() {
        routingCache.setLocalRoutingInfo("host", host);
        routingCache.setLocalRoutingInfo("port", port);
        routingCache.setLocalRoutingInfo("state", NodeState.ready);
    }
}
