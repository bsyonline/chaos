package com.rolex.rpc.processor.impl;

import com.alibaba.fastjson.JSONObject;
import com.rolex.rpc.CommandType;
import com.rolex.rpc.model.Msg;
import com.rolex.rpc.model.MsgBody;
import com.rolex.rpc.model.proto.MsgProto;
import com.rolex.rpc.processor.NettyProcessor;
import com.rolex.rpc.util.SerializationUtils;
import io.netty.channel.Channel;
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
public class PingProcessor implements NettyProcessor {
    @Override
    public void process(Channel channel, MsgBody msgBody) throws InterruptedException {
        MsgBody pong = new MsgBody();
        pong.setType(CommandType.PONG);
        byte[] content = SerializationUtils.serialize(pong, MsgBody.class);
        Msg msg = new Msg(content.length, content);
        channel.writeAndFlush(msg).sync();
        log.info("receive heartbeat {} and response {}", JSONObject.toJSONString(msgBody), JSONObject.toJSONString(pong));
    }

    @Override
    public void process4proto(Channel channel, MsgProto msg) throws InterruptedException {
        MsgProto pong = MsgProto.newBuilder()
                .setType(MsgProto.CommandType.PONG)
                .setJobId(msg.getJobId())
                .build();
        channel.writeAndFlush(pong).sync();
        log.info("receive heartbeat {} and response {}", msg, pong);
    }
}
