/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author rolex
 * @since 2020
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder的encode方法执行，将" + msg + "(Long)转成Byte");
        out.writeLong(msg);
    }
}
