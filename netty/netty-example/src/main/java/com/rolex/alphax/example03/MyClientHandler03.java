/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example03;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author rolex
 * @since 2020
 */
public class MyClientHandler03 extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("MyClientHandler的channelRead0执行");
        System.out.println("客户端收到数据：" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler的channelActive执行,发送数据1234L");
        ctx.writeAndFlush(1234L);
    }
}
