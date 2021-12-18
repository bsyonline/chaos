/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.netty;

import com.rolex.alphax.model.RpcRequest;
import com.rolex.alphax.netty.codec.MsgDecoder;
import com.rolex.alphax.netty.codec.MsgEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author rolex
 * @since 2020
 */
@Component
@Slf4j
public class NettyClient {
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static NettyClientHandler clientHandler;
    private static Bootstrap bootstrap;
    private ChannelHandlerContext ctx;

    public <T> T create(final Class<?> c, final String serviceVersion) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{c},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        log.info("remote call");
                        if (Object.class.equals(method.getDeclaringClass())) {
                            return method.invoke(this, args);
                        }
                        return rpcInvoke(method, args);
                    }
                });
    }

    @SneakyThrows
    private Object rpcInvoke(Method method, Object[] args) {
        if (clientHandler == null) {
            init();
        }
        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setInterfaceName(method.getDeclaringClass().getName());
        request.setServiceVersion("v1.0");
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        clientHandler.setRpcRequest(request);
        return executorService.submit(clientHandler).get();
    }

    public static void init() {
        clientHandler = new NettyClientHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline
                                    .addLast(new IdleStateHandler(3, 3, 5, TimeUnit.SECONDS))
                                    .addLast(new MsgDecoder())
                                    .addLast(new MsgEncoder())
                                    .addLast(clientHandler);
                        }
                    });
            doConnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doConnect() {
        ChannelFuture channelFuture = bootstrap.connect("localhost", 8000);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!channelFuture.isSuccess()) {
                    final EventLoop loop = channelFuture.channel().eventLoop();
                    loop.schedule(new Runnable() {
                        @Override
                        public void run() {
                            log.error("服务端链接不上，开始重连操作...");
                            init();
                        }
                    }, 1L, TimeUnit.SECONDS);
                } else {
                    log.info("服务端链接成功...");
                }
            }
        });
    }
}
