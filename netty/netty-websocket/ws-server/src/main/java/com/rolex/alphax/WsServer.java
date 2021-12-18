/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.alphax;

import com.rolex.alphax.handler.WsServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2021
 */
@Slf4j
public class WsServer {

    private final int port;

    public WsServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        WsServer gateway = new WsServer(8080);
        gateway.start();
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                //.handler(new LoggingHandler("INFO"))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
//                        pipeline.addLast(new SecurityServerHandler());
                        pipeline.addLast(new WebSocketServerProtocolHandler("/ws", "*", true));
                        pipeline.addLast(new WsServerHandler());
                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            log.info("server started on port 8080");
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
