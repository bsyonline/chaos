/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
public class MyByteToLongDecoder extends /*ByteToMessageDecoder*/ ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder的decode执行，将Byte转成Long");
        //if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        //}
    }

}
