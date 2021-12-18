/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.rpc.netty;

import com.rolex.alphax.rpc.provider.UserServiceImpl;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author rolex
 * @since 2020
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到请求=" + msg);
        ByteBuf byteBuf = (ByteBuf) msg;
        String id = byteBuf.toString(CharsetUtil.UTF_8);
        String user = new UserServiceImpl().getUser(id);
        ctx.writeAndFlush(Unpooled.wrappedBuffer(user.getBytes(CharsetUtil.UTF_8)));
        System.out.println("服务器响应");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
