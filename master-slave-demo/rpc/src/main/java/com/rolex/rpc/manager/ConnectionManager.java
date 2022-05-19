package com.rolex.rpc.manager;

import com.google.common.collect.Lists;
import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.discovery.util.Constants;
import com.rolex.rpc.handler.ProtoNettyClientHandler;
import com.rolex.rpc.model.proto.MsgProto;
import com.rolex.rpc.rebalance.RebalanceStrategy;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

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
    private RebalanceStrategy rebalanceStrategy;
    private final ProtoNettyClientHandler clientHandler;
//    private final NettyClientHandler clientHandler;

    public ConnectionManager(ProtoNettyClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.rebalanceStrategy = clientHandler.getServerSelectorStrategy();
        this.connected = false;
    }
//    public ConnectionManager(NettyClientHandler clientHandler) {
//        this.clientHandler = clientHandler;
//        this.rebalanceStrategy = clientHandler.getServerSelectorStrategy();
//        this.connected = false;
//    }

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
        Map<Host, RoutingInfo> map = clientHandler.getRoutingCache().getRoutingInfo().get(NodeType.server);
        Host host = rebalanceStrategy.select(Lists.newArrayList(map.values()));
        log.info("starting connect to server {}", host);
        start(host);
    }

    public void reconnect() throws InterruptedException {
        Thread.sleep(Constants.BROADCAST_TIME_MILLIS * 3 + 1000);
        waitingForServerOnline();
        Map<Host, RoutingInfo> map = clientHandler.getRoutingCache().getRoutingInfo().get(NodeType.server);
        Host host = rebalanceStrategy.select(Lists.newArrayList(map.values()));
        log.info("断线重连 {}", host);
        start(host);
    }

    private void waitingForServerOnline() throws InterruptedException {
        while (!connected) {
            Map<NodeType, Map<Host, RoutingInfo>> registry = clientHandler.getRoutingCache().getRoutingInfo();
            Map<Host, RoutingInfo> serverInfo = registry.get(NodeType.server);
            if (serverInfo == null || serverInfo.isEmpty()) {
                log.info("等待server上线");
                Thread.sleep(Constants.WAIT_SERVER_TIME_MILLIS);
            } else {
                connected = true;
                log.info("发现server的注册信息：{}", serverInfo);
                Thread.sleep(10000);
                log.info("10s后发现server的注册信息：{}", serverInfo);
            }
        }
    }

    public void start(Host host) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_SNDBUF, 65535)
                .option(ChannelOption.SO_RCVBUF, 65535)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                        pipeline.addLast("idle-state-handler", new IdleStateHandler(5, 0, 0));
//                        pipeline.addLast("decoder", new MsgDecoder());
//                        pipeline.addLast("encoder", new MsgEncoder());
                        pipeline.addLast(new ProtobufVarint32FrameDecoder());
                        pipeline.addLast(new ProtobufDecoder(MsgProto.getDefaultInstance()));
                        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                        pipeline.addLast(new ProtobufEncoder());
                        pipeline.addLast("client-handler", clientHandler);
                    }
                });

        ChannelFuture future = bootstrap.connect(host.getHost(), host.getPort()).sync();
        future.channel().closeFuture();
    }

}
