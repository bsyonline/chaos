/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.master.server;

import com.rolex.master.handler.NettyServerHandler;
import com.rolex.master.manager.DefaultExecutorManager;
import com.rolex.master.manager.ExecutorManager;
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

/**
 * @author rolex
 * @since 2019
 */
@Slf4j
public class NettyServer {

    String host;
    int port;

    ExecutorManager executorManager;

    public NettyServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public NettyServer executorManager(ExecutorManager executorManager) {
        this.executorManager = executorManager;
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
                            pipeline.addLast(new IdleStateHandler(0, 0, 100));
                            pipeline.addLast(new NettyServerHandler(executorManager));
                        }
                    })
                    .localAddress(host, port);

            ChannelFuture future = bootstrap.bind().sync();
            log.info("server started on port {}", port);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
