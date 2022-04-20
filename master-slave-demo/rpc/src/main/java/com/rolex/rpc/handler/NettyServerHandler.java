/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.rpc.handler;

import com.alibaba.fastjson.JSONObject;
import com.rolex.rpc.CommandType;
import com.rolex.rpc.NettyServer;
import com.rolex.rpc.model.Manager;
import com.rolex.rpc.model.Msg;
import com.rolex.rpc.model.MsgBody;
import com.rolex.rpc.processor.NettyRequestProcessor;
import com.rolex.rpc.util.Pair;
import com.rolex.rpc.util.SerializationUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.internal.ChannelUtils;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author rolex
 * @since 2019
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<Msg> {

    private final ConcurrentHashMap<CommandType, Pair<NettyRequestProcessor, ExecutorService>> processors = new ConcurrentHashMap<>();
    static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    Manager manager;
    NettyServer nettyServer;

    public NettyServerHandler(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    public void setExecutorManager(Manager manager) {
        this.manager = manager;
    }

    public void registerProcessor(final CommandType commandType, final NettyRequestProcessor processor) {
        this.registerProcessor(commandType, processor, null);
    }

    /**
     * register processor
     *
     * @param commandType command type
     * @param processor processor
     * @param executor thread executor
     */
    public void registerProcessor(final CommandType commandType, final NettyRequestProcessor processor, final ExecutorService executor) {
        ExecutorService executorRef = executor;
        if (executorRef == null) {
            executorRef = nettyServer.getDefaultExecutor();
        }
        this.processors.putIfAbsent(commandType, new Pair<>(processor, executorRef));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
        log.info("name={}, id={}", ctx.name(), ctx.channel().id().asLongText());
        manager.addChannel(ctx.channel().id().asLongText(), ctx.channel());
        System.out.println(ctx.channel().remoteAddress() + "加入");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        manager.removeChannel(ctx.channel().id().asLongText());
        System.out.println(ctx.channel().remoteAddress() + "下线");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        byte[] content = msg.getContent();
        // 反序列化
        MsgBody msgBody = (MsgBody) SerializationUtils.deserialize(content, MsgBody.class);
        processReceived(ctx.channel(), msgBody);
    }

    /**
     * process received logic
     *
     * @param channel channel
     * @param msg message
     */
    private void processReceived(final Channel channel, final MsgBody msg) {
        final CommandType commandType = msg.getType();
        final Pair<NettyRequestProcessor, ExecutorService> pair = processors.get(commandType);
        if (pair != null) {
            Runnable r = () -> {
                try {
                    pair._1().process(channel, msg);
                } catch (Exception ex) {
                    log.error("process msg {} error", msg, ex);
                }
            };
            try {
                pair._2().submit(r);
            } catch (RejectedExecutionException e) {
                log.warn("thread pool is full, discard msg {} from {}", msg, channel.remoteAddress().toString());
            }
        } else {
            log.warn("commandType {} not support", commandType);
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
                    log.debug("read idle and close connect");
                    break;
                case WRITER_IDLE:
                    log.debug("write idle");
                    break;
                case ALL_IDLE:
                    log.debug("all idle and close channel");
                    ctx.close().sync();
                    break;
                default:
                    break;
            }
        }
    }

}
