/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example04;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author rolex
 * @since 2020
 */
public class MyClientHandler04 extends SimpleChannelInboundHandler<Msg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        System.out.println("客户端收到数据长度=" + msg.getLength() + " 数据=" + new String(msg.content, CharsetUtil.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            byte[] buf = "hello world".getBytes(CharsetUtil.UTF_8);
            int length = buf.length;
            Msg message = new Msg(length, buf);
            ctx.writeAndFlush(message);
        }
    }
}
