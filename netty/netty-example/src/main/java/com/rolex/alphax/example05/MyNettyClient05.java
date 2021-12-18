/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example05;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author rolex
 * @since 2020
 */
public class MyNettyClient05 {
    private final String host;
    private final int port;

    public MyNettyClient05(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MyClientHandler05());

                        }
                    });
            bootstrap.connect(host, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MyNettyClient05("localhost", 7000).connect();
    }
}
