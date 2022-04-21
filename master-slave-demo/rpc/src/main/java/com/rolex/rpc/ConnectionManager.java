package com.rolex.rpc;

import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.discovery.util.Constants;
import com.rolex.rpc.codec.MsgDecoder;
import com.rolex.rpc.codec.MsgEncoder;
import com.rolex.rpc.handler.NettyClientHandler;
import com.rolex.rpc.model.ServerInfo;
import com.rolex.rpc.rebalance.Strategy;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class ConnectionManager {
    volatile boolean connected = false;
    private Strategy serverSelectorStrategy;
    private final NettyClientHandler clientHandler;

    public ConnectionManager(NettyClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.serverSelectorStrategy = clientHandler.getServerSelectorStrategy();
        this.connected = false;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void startConnect() throws InterruptedException {
        waitingForServerOnline();
        connect();
    }

    public void connect() throws InterruptedException {
        ServerInfo serverInfo = serverSelectorStrategy.select();
        log.info("starting connect to server {}", serverInfo);
        start(serverInfo);
    }

    public void reconnect() throws InterruptedException {
        Thread.sleep(Constants.BROADCAST_TIME_MILLIS * 3 + 1000);
        waitingForServerOnline();
        ServerInfo serverInfo = serverSelectorStrategy.select();
        log.info("断线重连 {}", serverInfo);
        start(serverInfo);
    }

    private void waitingForServerOnline() throws InterruptedException {
        while (!connected) {
            Map<NodeType, Map<Integer, RoutingInfo>> registry = RoutingCache.getRoutingInfo();
            Map<Integer, RoutingInfo> serverInfo = registry.get("server");
            if (serverInfo == null || serverInfo.isEmpty()) {
                log.info("等待master上线");
                Thread.sleep(3000);
            } else {
                connected = true;
                log.info("发现server的注册信息：{}", serverInfo);
            }
        }
    }

    public void start(ServerInfo serverInfo) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(serverInfo.getHost(), serverInfo.getPort()))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("idle-state-handler", new IdleStateHandler(5, 0, 0));
                        pipeline.addLast("decoder", new MsgDecoder());
                        pipeline.addLast("encoder", new MsgEncoder());
                        pipeline.addLast("client-handler", clientHandler);
                    }
                });
        ChannelFuture future = bootstrap.connect().sync();
        future.channel().closeFuture().sync();
    }

}
