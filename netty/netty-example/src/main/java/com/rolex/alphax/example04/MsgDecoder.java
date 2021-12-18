/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example04;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("byte to msg");
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);
        Msg msg = new Msg(length, content);
        out.add(msg);
    }
}
