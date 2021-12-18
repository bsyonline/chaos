/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example02;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author rolex
 * @since 2020
 */
public class MyServerInitializer02 extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new MyServerHandler02());
    }
}
