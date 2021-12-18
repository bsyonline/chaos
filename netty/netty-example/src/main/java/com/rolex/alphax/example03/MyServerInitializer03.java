/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example03;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author rolex
 * @since 2020
 */
public class MyServerInitializer03 extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 加入入站的解码器
//        pipeline.addLast(new MyByteToLongDecoder());
//        pipeline.addLast(new MyLongToByteEncoder());
        pipeline.addLast(new CombinedCodec(new MyByteToLongDecoder(), new MyLongToByteEncoder()));
        pipeline.addLast(new MyServerHandler03());
    }
}
