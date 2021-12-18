/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example04;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @author rolex
 * @since 2020
 */
public class MyServerHandler04 extends SimpleChannelInboundHandler<Msg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        System.out.println("服务器收到数据长度=" + msg.length + " 数据=" + new String(msg.getContent(), CharsetUtil.UTF_8));

        byte[] response = UUID.randomUUID().toString().getBytes(CharsetUtil.UTF_8);
        ctx.writeAndFlush(new Msg(response.length, response));
    }
}
