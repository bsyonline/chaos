/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.slave.client;

import com.rolex.common.cache.RouteCache;
import com.rolex.common.model.RouteInfo;
import com.rolex.slave.handler.NettyClientHandler;
import com.rolex.slave.model.ServerInfo;
import com.rolex.slave.rpc.ServerSelectorStrategy;
import com.rolex.slave.rpc.rebalance.RandomSelector;
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

/**
 * @author rolex
 * @since 2019
 */
@Slf4j
public class NettyClient {
    volatile boolean connected = false;
    private ServerSelectorStrategy serverSelectorStrategy = new RandomSelector();

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
        ServerInfo serverInfo = serverSelectorStrategy.selectServer();
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
                        pipeline.addLast(new IdleStateHandler(30, 30, 50));
                        pipeline.addLast(new NettyClientHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect().sync();
        future.channel().closeFuture().sync();
    }
}
