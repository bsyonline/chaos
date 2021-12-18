/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.example03;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @author rolex
 * @since 2020
 */
public class CombinedCodec extends CombinedChannelDuplexHandler<MyByteToLongDecoder, MyLongToByteEncoder> {
    public CombinedCodec(MyByteToLongDecoder inboundHandler, MyLongToByteEncoder outboundHandler) {
        super(inboundHandler, outboundHandler);
    }
}
