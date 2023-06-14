package com.rolex.handler;

import com.rolex.Response;
import com.rolex.SyncWriteFuture;
import com.rolex.SyncWriteMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


import static com.rolex.Server.channels;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<Response> {
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
//        log.info("收到server的消息：{}", msg);
//
//        channelHandlerContext.channel().writeAndFlush("ack");
//
//
//
//
//    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channels.put("channel", ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        String requestId = response.getRequestId();
        SyncWriteFuture future = (SyncWriteFuture) SyncWriteMap.syncKey.get(requestId);
        if (future != null) {
            future.setResponse(response);
        }
    }
}
