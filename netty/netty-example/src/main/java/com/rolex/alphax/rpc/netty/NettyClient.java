/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author rolex
 * @since 2020
 */
public class NettyClient {
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static NettyClientHandler clientHandler;

    public Object getInstance(final Class<?> c, final String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{c},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("执行反射代码");
                        if (clientHandler == null) {
                            init();
                        }
                        clientHandler.setParams(providerName + args[0]);
                        return executorService.submit(clientHandler).get();
                    }
                });
    }

    public static void init() {
        clientHandler = new NettyClientHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline
//                                    .addLast(new StringDecoder())
//                                    .addLast(new StringEncoder())
                                    .addLast(clientHandler);
                        }
                    });
            bootstrap.connect("localhost", 7000).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            group.shutdownGracefully();
        }
    }
}
