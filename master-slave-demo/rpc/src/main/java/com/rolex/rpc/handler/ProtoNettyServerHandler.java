/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.rpc.handler;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.util.Pair;
import com.rolex.rpc.CommandType;
import com.rolex.rpc.NettyServer;
import com.rolex.rpc.model.Manager;
import com.rolex.rpc.model.Msg;
import com.rolex.rpc.model.MsgBody;
import com.rolex.rpc.model.proto.MsgProto;
import com.rolex.rpc.processor.NettyProcessor;
import com.rolex.rpc.util.SerializationUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author rolex
 * @since 2019
 */
@Slf4j
@ChannelHandler.Sharable
public class ProtoNettyServerHandler extends SimpleChannelInboundHandler<MsgProto> {

    private final ConcurrentHashMap<MsgProto.CommandType, Pair<NettyProcessor, ExecutorService>> processors = new ConcurrentHashMap<>();
//    private final ConcurrentHashMap<CommandType, Pair<NettyProcessor, ExecutorService>> processors = new ConcurrentHashMap<>();
    static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    Manager manager;
    NettyServer nettyServer;
    RoutingCache routingCache;

    public ProtoNettyServerHandler(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    public void setRoutingCache(RoutingCache routingCache) {
        this.routingCache = routingCache;
    }

    public void setExecutorManager(Manager manager) {
        this.manager = manager;
    }

    public void registerProcessor(final MsgProto.CommandType commandType, final NettyProcessor processor) {
        this.registerProcessor(commandType, processor, null);
    }

    /**
     * register processor
     *
     * @param commandType command type
     * @param processor   processor
     * @param executor    thread executor
     */
    public void registerProcessor(final MsgProto.CommandType commandType, final NettyProcessor processor, final ExecutorService executor) {
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
        manager.addChannel(getClientHost(ctx), ctx.channel());
        routingCache.addConnect(getClientHost(ctx), ctx.channel());
        log.info("客户端ip地址：{}", getClientHost(ctx));
        log.info("连接的server地址：{}", getServerHost(ctx));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        manager.removeChannel(getClientHost(ctx));
        routingCache.removeConnect(getClientHost(ctx));
        routingCache.clearLocalRoutingInfo();
        System.out.println(ctx.channel().remoteAddress() + "下线");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgProto msg) throws Exception {
        processReceived(ctx.channel(), msg);
    }

    private Host getServerHost(ChannelHandlerContext ctx) {
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        int localPort = socketChannel.localAddress().getPort();
        String localAddress = socketChannel.localAddress().getAddress().getHostAddress();
        return Host.of(localAddress, localPort);
    }

    private Host getClientHost(ChannelHandlerContext ctx) {
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        int port = ipSocket.getPort();
        return Host.of(clientIp, port);
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
