/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.rpc.handler;

import com.rolex.discovery.routing.LocalRoutingInfo;
import com.rolex.discovery.routing.NodeState;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.rpc.CommandType;
import com.rolex.rpc.manager.ConnectionManager;
import com.rolex.rpc.model.Msg;
import com.rolex.rpc.model.MsgBody;
import com.rolex.rpc.processor.NettyProcessor;
import com.rolex.rpc.rebalance.Strategy;
import com.rolex.discovery.util.Pair;
import com.rolex.rpc.util.SerializationUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author rolex
 * @since 2019
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<Msg> {

    private final ExecutorService defaultExecutor = Executors.newFixedThreadPool(5);
    private final ConcurrentHashMap<CommandType, Pair<NettyProcessor, ExecutorService>> processors = new ConcurrentHashMap<>();
    private Strategy serverSelectorStrategy;
    private RoutingCache routingCache;

    public RoutingCache getRoutingCache() {
        return routingCache;
    }

    public void setRoutingCache(RoutingCache routingCache) {
        this.routingCache = routingCache;
    }

    public Strategy getServerSelectorStrategy() {
        return serverSelectorStrategy;
    }

    public void setServerSelectorStrategy(Strategy serverSelectorStrategy) {
        this.serverSelectorStrategy = serverSelectorStrategy;
    }

    public void registerProcessor(final CommandType commandType, final NettyProcessor processor) {
        this.registerProcessor(commandType, processor, null);
    }

    public void registerProcessor(final CommandType commandType, final NettyProcessor processor, final ExecutorService executor) {
        ExecutorService executorRef = executor;
        if (executorRef == null) {
            executorRef = defaultExecutor;
        }
        this.processors.putIfAbsent(commandType, new Pair<>(processor, executorRef));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("master {} offline and reconnect", ctx.channel().remoteAddress().toString());
        new ConnectionManager(this).reconnect();

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        setClientLocalRoutingInfo(ctx);
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
     * @param msg     message
     */
    private void processReceived(final Channel channel, final MsgBody msg) {
        final CommandType commandType = msg.getType();
        final Pair<NettyProcessor, ExecutorService> pair = processors.get(commandType);
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
                    log.debug("read idle");
                    heartbeat(ctx);
                    break;
                case WRITER_IDLE:
                    log.debug("write idle");
                    heartbeat(ctx);
                    break;
                case ALL_IDLE:
                    log.debug("all idle and send PING");
                    heartbeat(ctx);
                    break;
            }
        }
    }

    private void heartbeat(ChannelHandlerContext ctx) {
        MsgBody request = new MsgBody();
        request.setType(CommandType.PING);
        byte[] content = SerializationUtils.serialize(request, MsgBody.class);
        Msg msg = new Msg(content.length, content);
        ctx.writeAndFlush(msg);
    }

    private void setClientLocalRoutingInfo(ChannelHandlerContext ctx){
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        int localPort = socketChannel.localAddress().getPort();
        String localAddress = socketChannel.localAddress().getAddress().getHostAddress();

        routingCache.setLocalRoutingInfo("host", localAddress);
        routingCache.setLocalRoutingInfo("port", localPort);
        routingCache.setLocalRoutingInfo("state", NodeState.ready);
    }

}
