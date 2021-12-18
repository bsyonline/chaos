/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.rpc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.Callable;

/**
 * @author rolex
 * @since 2020
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext ctx;
    private String result;
    private String params;

    @Override
    public synchronized Object call() throws Exception {
        System.out.println("call 被调用");
        // 发送数据给server，然后wait，等待唤醒后继续执行
        ctx.writeAndFlush(Unpooled.wrappedBuffer(params.getBytes(CharsetUtil.UTF_8)));
        System.out.println("数据[" + params + "]已发送");
        System.out.println("ctx=" + ctx);
        wait();
        return result;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive 被调用");
        this.ctx = ctx;
        System.out.println("ctx=" + ctx);
        System.out.println("ctx赋值");
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead 被调用 before notify");
        ByteBuf byteBuf = (ByteBuf) msg;
        result = byteBuf.toString(CharsetUtil.UTF_8);
        notify();// 唤醒等待的线程
        System.out.println("channelRead 被调用 after notify");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public void setParams(String params) {
        System.out.println("设置参数");
        this.params = params;
    }
}
