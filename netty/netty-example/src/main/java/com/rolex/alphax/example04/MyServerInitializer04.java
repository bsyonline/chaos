/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example04;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author rolex
 * @since 2020
 */
public class MyServerInitializer04 extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MsgDecoder());
        pipeline.addLast(new MsgEncoder());
        pipeline.addLast(new MyServerHandler04());
    }
}
