/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.netty;

import com.rolex.alphax.netty.codec.MsgDecoder;
import com.rolex.alphax.netty.codec.MsgEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author rolex
 * @since 2020
 */
@Component
public class NettyServer {
    public void startServer(int port) {
        startServer0(port);
    }

    private void startServer0(int port) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline
                                    .addLast(new IdleStateHandler(0, 0, 10, TimeUnit.SECONDS))
                                    .addLast(new MsgDecoder())
                                    .addLast(new MsgEncoder())
                                    .addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            System.out.println("RPC Provider 启动");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
