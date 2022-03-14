/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.slave.handler;

import com.rolex.slave.exec.MainReactor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * @author rolex
 * @since 2019
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) throws Exception {
        ByteBuf buf = (ByteBuf) data;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.getBytes(0, bytes);
        String msg = new String(bytes);

        if ("PONG".equals(msg)) {
            log.info("receive heartbeat");
        } else {
            log.info("receive msg {}", msg);
            MainReactor.accept(msg);
            log.info("收到派发的作业 {} 并返回ack", msg);
            ctx.writeAndFlush(Unpooled.copiedBuffer("ack:" + msg, UTF_8));
        }
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        while (true) {
//            log.info("send msg {} to server", "hello");
//            ctx.writeAndFlush(Unpooled.copiedBuffer("hello", UTF_8));
//            Thread.sleep(15000);
//        }
//    }

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
                    log.info("read idle");
                    ctx.writeAndFlush(Unpooled.copiedBuffer("PING", //2
                            UTF_8));
                    break;
                case WRITER_IDLE:
                    log.info("write idle");
                    ctx.writeAndFlush(Unpooled.copiedBuffer("PING", //2
                            UTF_8));
                    break;
                case ALL_IDLE:
                    log.info("all idle and send PING");
                    ctx.writeAndFlush(Unpooled.copiedBuffer("PING", //2
                            UTF_8));
                    break;
            }
        }
    }
}
