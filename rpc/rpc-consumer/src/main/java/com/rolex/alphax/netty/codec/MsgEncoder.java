/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.netty.codec;

import com.rolex.alphax.model.Msg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class MsgEncoder extends MessageToByteEncoder<Msg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf out) throws Exception {
        log.info("msg to byte: msg length={}, content={}", msg.getLength(), new String(msg.getContent()));
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
