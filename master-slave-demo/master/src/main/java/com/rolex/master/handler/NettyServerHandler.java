/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.master.handler;

import com.rolex.master.manager.ExecutorManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * @author rolex
 * @since 2019
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    ExecutorManager executorManager;

    public NettyServerHandler(ExecutorManager executorManager) {
        this.executorManager = executorManager;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
        log.info("name={}, id={}", ctx.name(), ctx.channel().id().asLongText());
        executorManager.addChannel(ctx.channel().id().asLongText(), ctx.channel());
        System.out.println(ctx.channel().remoteAddress() + "加入");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        executorManager.removeChannel(ctx.channel().id().asLongText());
        System.out.println(ctx.channel().remoteAddress() + "下线");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) throws Exception {
        ByteBuf buf = (ByteBuf) data;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.getBytes(0, bytes);
        String msg = new String(bytes);
        if ("PING".equals(msg)) {
            log.info("receive msg {} and response PONG, client is alive", new String(bytes));
            ctx.writeAndFlush(Unpooled.copiedBuffer("PONG", UTF_8));
        } else if (msg.startsWith("ack:")) {
            log.info("receive ack {}", msg);
        } else {
            log.info("receive msg {} and response same msg", msg);
            ctx.writeAndFlush(Unpooled.copiedBuffer(msg, UTF_8));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    log.info("read idle and close connect");
                    ctx.close().sync();
                    break;
                case WRITER_IDLE:
                    log.info("write idle");
                    break;
                case ALL_IDLE:
                    log.info("all idle");
                    break;
                default:
                    break;
            }
        }
    }
}
