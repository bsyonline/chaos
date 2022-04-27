package com.rolex.rpc.processor.impl;

import com.alibaba.fastjson.JSONObject;
import com.rolex.rpc.model.MsgBody;
import com.rolex.rpc.model.proto.MsgProto;
import com.rolex.rpc.processor.NettyProcessor;
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
public class ReceiveAckProcessor implements NettyProcessor {
    @Override
    public void process(Channel channel, MsgBody msg) throws InterruptedException {
        log.info("receive ack: {}", JSONObject.toJSONString(msg));
    }

    @Override
    public void process4proto(Channel channel, MsgProto msg) throws InterruptedException {
        log.info("receive ack: {}", msg);
    }
}
