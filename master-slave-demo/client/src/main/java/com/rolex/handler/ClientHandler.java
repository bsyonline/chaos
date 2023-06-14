package com.rolex.handler;

import com.rolex.Request;
import com.rolex.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<Request> {
    private final ExecutorService callbackExecutor = new ThreadPoolExecutor(5, 10, 1, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(1000));
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
//        log.info("收到server的消息：{}", msg);
//
//        ResponseFuture future = ResponseFuture.getFuture(1);
//        if (future != null) {
//            future.setResponseCommand(msg);
//            future.release();
//            if (future.getInvokeCallback() != null) {
//                this.callbackExecutor.submit(future::executeInvokeCallback);
//            } else {
//                future.putResponse(msg);
//            }
//        } else {
//            channelHandlerContext.channel().writeAndFlush(Unpooled.wrappedBuffer("ack".getBytes(StandardCharsets.UTF_8)));
//        }
//
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        //反馈
        Response response = new Response();
        response.setRequestId(request.getRequestId());
        response.setParam(request.getResult() + " 请求成功，反馈结果请接受处理。");
        log.info("{}", response.getParam());
        channelHandlerContext.writeAndFlush(request);
        //释放
        ReferenceCountUtil.release(request);
    }
}
