/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example05;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author rolex
 * @since 2020
 */
public class MyServerHandler05 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("收到客户端请求：" + byteBuf.toString(CharsetUtil.UTF_8));
        // 提交到eventLoop的tackQueue
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                myTask1();
            }
        });

        // 提交到eventLoop的schedule
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                myTask2();
            }
        }, 0, TimeUnit.SECONDS);

        System.out.println();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.wrappedBuffer("world".getBytes(CharsetUtil.UTF_8)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private void myTask1() {
        System.out.println("开始执行任务myTask1");
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("myTask1任务执行结束");
    }

    private void myTask2() {
        System.out.println("开始执行任务myTask2");
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("myTask2任务执行结束");
    }

}
