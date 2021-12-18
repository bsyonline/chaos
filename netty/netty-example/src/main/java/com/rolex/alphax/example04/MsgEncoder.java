/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example04;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author rolex
 * @since 2020
 */
public class MsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf out) throws Exception {
        System.out.println("msg to byte");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
