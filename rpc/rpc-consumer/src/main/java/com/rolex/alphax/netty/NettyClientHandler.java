/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.netty;

import com.rolex.alphax.model.Msg;
import com.rolex.alphax.model.RpcRequest;
import com.rolex.alphax.model.RpcResponse;
import com.rolex.alphax.util.SerializationUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<Msg> implements Callable {

    private ChannelHandlerContext ctx;
    private Object result;
    private RpcRequest rpcRequest;

    @Override
    public synchronized Object call() throws Exception {
        log.info("call 被调用");
        // 发送数据给server，然后wait，等待唤醒后继续执行
        byte[] content = SerializationUtils.serialize(rpcRequest, RpcRequest.class);
        Msg message = new Msg(content.length, content);
        ctx.writeAndFlush(message);
        log.info("数据[{}]已发送", rpcRequest);
        wait();
        return result;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("ctx 赋值");
        this.ctx = ctx;
        heartbeat(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("连接异常");
        ctx.close();
        this.ctx = null;
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                log.info("进行重连");
                NettyClient.doConnect();
            }
        }, 3, TimeUnit.SECONDS);
    }

    @Override
    protected synchronized void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        byte[] content = msg.getContent();
        RpcResponse rpcResponse = (RpcResponse) SerializationUtils.deserialize(content, RpcResponse.class);
        log.info("收到响应：rpcResponse={}", rpcResponse);
        this.result = rpcResponse.getResult();
        notifyAll();
    }

    public void setRpcRequest(RpcRequest rpcRequest) {
        this.rpcRequest = rpcRequest;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接关闭");
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    break;
                case WRITER_IDLE:
                    break;
                case ALL_IDLE:
                    heartbeat(ctx);
                    break;
                default:
                    break;
            }
        }
    }

    private void heartbeat(ChannelHandlerContext ctx) {
        RpcRequest request = new RpcRequest();
        request.setMethodName("PING");
        byte[] content = SerializationUtils.serialize(request, RpcRequest.class);
        Msg msg = new Msg(content.length, content);
        ctx.writeAndFlush(msg);
    }

}
