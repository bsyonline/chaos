package com.rolex.alphax.example03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyDecoder extends ReplayingDecoder<Void> {

    private final Queue<Integer> values = new LinkedList<Integer>();

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {

        // A message contains 2 integers.
        values.offer(buf.readInt());
        values.offer(buf.readInt());

        // This assertion will fail intermittently since values.offer()
        // can be called more than two times!
        assert values.size() == 2;
        out.add(values.poll() + values.poll());
    }

}