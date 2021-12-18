/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.chat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author rolex
 * @since 2020
 */
public class MyChatServerHandler extends ChannelInboundHandlerAdapter {


    static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.writeAndFlush(Unpooled.wrappedBuffer((ctx.channel().remoteAddress() + "加入聊天").getBytes(CharsetUtil.UTF_8)));
        channels.add(ctx.channel());
        System.out.println(ctx.channel().remoteAddress() + "上线了");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "下线了");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String message = byteBuf.toString(CharsetUtil.UTF_8);
        channels.forEach(ch -> {
            if(ch != ctx.channel()) {
                ch.writeAndFlush(Unpooled.wrappedBuffer((ctx.channel().remoteAddress()+ "说：" + message).getBytes(CharsetUtil.UTF_8)));
            }else{
                ch.writeAndFlush(Unpooled.wrappedBuffer(("你说：" + message).getBytes(CharsetUtil.UTF_8)));
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
