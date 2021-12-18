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
public class MyServerHandler03 extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("MyServerHandler的channelRead0执行");
        System.out.println("服务器收到数据：" + msg);

        ctx.channel().writeAndFlush(5678L);
    }
}
