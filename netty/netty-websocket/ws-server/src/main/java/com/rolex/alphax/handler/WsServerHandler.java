/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.alphax.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;

/**
 * @author rolex
 * @since 2021
 */
@Slf4j
public class WsServerHandler extends SimpleChannelInboundHandler<Object> {
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private WebSocketServerHandshaker handShaker;
    private static final String WEB_SOCKET_URL = "ws://localhost:8080/ws";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        group.add(ctx.channel());
        System.out.println("客户端与服务端开启链接");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        group.remove(ctx.channel());
        System.out.println("客户端与服务端断开链接");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof CloseWebSocketFrame) {
            handShaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new RuntimeException("[" + this.getClass().getName() + "]不支持消息");
        }
        String request = ((TextWebSocketFrame) frame).text();
        System.out.println(">>>>>>>>" + request);
        TextWebSocketFrame twf = new TextWebSocketFrame(new Date().toString() + ctx.channel().id() + ">>>>>" + request + "\r\n");
        group.writeAndFlush(twf);
    }

    private void handHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(WEB_SOCKET_URL, null, false);
        handShaker = wsFactory.newHandshaker(request);
        if (handShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handShaker.handshake(ctx.channel(), request);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, DefaultFullHttpResponse res) {
        if (res.status().code() == 200) {
            ByteBuf buf = Unpooled.copiedBuffer("" + res.status().code(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(request) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
