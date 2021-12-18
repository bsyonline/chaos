/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.netty;

import com.rolex.alphax.model.Msg;
import com.rolex.alphax.model.RpcRequest;
import com.rolex.alphax.model.RpcResponse;
import com.rolex.alphax.model.User;
import com.rolex.alphax.service.UserService;
import com.rolex.alphax.service.impl.UserServiceImpl;
import com.rolex.alphax.util.SerializationUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;


/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<Msg> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        byte[] content = msg.getContent();
        // 反序列化
        RpcRequest request = (RpcRequest) SerializationUtils.deserialize(content, RpcRequest.class);
        // 通过反射调用
        Object serviceBean = new UserServiceImpl();
        Class<?> serviceClass = UserService.class;
        String methodName = request.getMethodName();
        if ("PING".equals(methodName)) {
            log.info("心跳信息 from " + ctx.channel().remoteAddress());

        } else {
            Class<?>[] parameterTypes = request.getParameterTypes();
            Object[] parameters = request.getParameters();
            Method method = serviceClass.getMethod(methodName, parameterTypes);
            method.setAccessible(true);
            User user = (User) method.invoke(serviceBean, parameters);
            RpcResponse response = new RpcResponse();
            response.setRequestId(request.getRequestId());
            response.setResult(user);
            // 序列化
            byte[] result = SerializationUtils.serialize(response, RpcResponse.class);
            // 发送
            ctx.writeAndFlush(new Msg(result.length, result));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("通道异常关闭");
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道建立");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道正常关闭");
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            log.info("心跳事件");
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    log.info("read idle");
                    break;
                case WRITER_IDLE:
                    log.info("write idle");
                    break;
                case ALL_IDLE:
                    log.info("10秒没有读写操作，关闭连接");
                    ctx.close().sync();
                    break;
                default:
                    break;
            }
        }
    }
}
