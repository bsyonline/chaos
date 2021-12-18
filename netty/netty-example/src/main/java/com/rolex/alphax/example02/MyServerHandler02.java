/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author rolex
 * @since 2020
 */
public class MyServerHandler02 extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf aLong) throws Exception {
        System.out.println("服务器收到消息：" + aLong.toString(CharsetUtil.UTF_8));
        ctx.writeAndFlush(Unpooled.copiedBuffer("world", CharsetUtil.UTF_8));
    }
}
