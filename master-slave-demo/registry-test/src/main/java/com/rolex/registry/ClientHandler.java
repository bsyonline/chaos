package com.rolex.registry;

import com.ksyun.kbdp.dts.model.event.protobuf.DtsMsgWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<DtsMsgWrapper.DtsMsg> {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("===========channelRegistered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("===========channelUnregistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("===========channelActive");
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        String localAddress = socketChannel.localAddress().getAddress().getHostAddress();

        DtsMsgWrapper.DtsMsg registry = DtsMsgWrapper.DtsMsg.newBuilder()
                .setType(DtsMsgWrapper.DtsMsg.CommandType.REGISTRY)
                .setExecutorType(DtsMsgWrapper.DtsMsg.ExecutorType.system)
                .setHost(localAddress)
                .setPort(1234)
                .build();
        log.info("registry={}", registry);
        ctx.channel().writeAndFlush(registry);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("===========channelInactive");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DtsMsgWrapper.DtsMsg msg) throws Exception {
        log.info("==========channelRead0,{}", msg);
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
        log.info("============heartbeat");
        DtsMsgWrapper.DtsMsg ping = DtsMsgWrapper.DtsMsg.newBuilder()
                .setType(DtsMsgWrapper.DtsMsg.CommandType.PING)
                .build();
        ctx.writeAndFlush(ping);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("============exceptionCaught", cause);
    }

}
