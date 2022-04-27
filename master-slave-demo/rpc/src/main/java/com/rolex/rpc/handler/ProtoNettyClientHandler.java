/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.rpc.handler;

import com.rolex.discovery.routing.NodeState;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.util.Pair;
import com.rolex.rpc.CommandType;
import com.rolex.rpc.manager.ConnectionManager;
import com.rolex.rpc.model.Msg;
import com.rolex.rpc.model.MsgBody;
import com.rolex.rpc.model.proto.MsgProto;
import com.rolex.rpc.processor.NettyProcessor;
import com.rolex.rpc.rebalance.RebalanceStrategy;
import com.rolex.rpc.util.SerializationUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

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
public class ProtoNettyClientHandler extends SimpleChannelInboundHandler<MsgProto> {

    private final ExecutorService defaultExecutor = Executors.newFixedThreadPool(5);
    private final ConcurrentHashMap<MsgProto.CommandType, Pair<NettyProcessor, ExecutorService>> processors = new ConcurrentHashMap<>();
//    private final ConcurrentHashMap<CommandType, Pair<NettyProcessor, ExecutorService>> processors = new ConcurrentHashMap<>();
    private RebalanceStrategy serverSelectorRebalanceStrategy;
    private RoutingCache routingCache;

    public RoutingCache getRoutingCache() {
        return routingCache;
    }

    public void setRoutingCache(RoutingCache routingCache) {
        this.routingCache = routingCache;
    }

    public RebalanceStrategy getServerSelectorStrategy() {
        return serverSelectorRebalanceStrategy;
    }

    public void setServerSelectorStrategy(RebalanceStrategy serverSelectorRebalanceStrategy) {
        this.serverSelectorRebalanceStrategy = serverSelectorRebalanceStrategy;
    }

    public void registerProcessor(final MsgProto.CommandType commandType, final NettyProcessor processor) {
        this.registerProcessor(commandType, processor, null);
    }

    public void registerProcessor(final MsgProto.CommandType commandType, final NettyProcessor processor, final ExecutorService executor) {
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
    protected void channelRead0(ChannelHandlerContext ctx, MsgProto msg) throws Exception {
        processReceived(ctx.channel(), msg);
    }

    /**
     * process received logic
     *
     * @param channel channel
     * @param msg     message
     */
    private void processReceived(final Channel channel, final MsgProto msg) {
        MsgProto.CommandType commandType = msg.getType();
        final Pair<NettyProcessor, ExecutorService> pair = processors.get(commandType);
        if (pair != null) {
            Runnable r = () -> {
                try {
                    pair._1().process4proto(channel, msg);
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
        MsgProto msg = MsgProto.newBuilder()
                .setType(MsgProto.CommandType.PING)
                .build();
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
