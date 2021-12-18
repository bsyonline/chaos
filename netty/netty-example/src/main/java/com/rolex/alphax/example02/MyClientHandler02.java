/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author rolex
 * @since 2020
 */
public class MyClientHandler02 extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf aLong) throws Exception {
        System.out.println("客户端收到消息：" + aLong.toString(CharsetUtil.UTF_8));
    }
}
