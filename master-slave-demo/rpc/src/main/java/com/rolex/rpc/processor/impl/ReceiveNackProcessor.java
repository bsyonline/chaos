package com.rolex.rpc.processor.impl;

import com.alibaba.fastjson.JSONObject;
import com.rolex.rpc.model.MsgBody;
import com.rolex.rpc.processor.NettyRequestProcessor;
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
public class ReceiveNackProcessor implements NettyRequestProcessor {
    @Override
    public void process(Channel channel, MsgBody msg) throws InterruptedException {
        log.info("receive nack: {}", JSONObject.toJSONString(msg));
    }
}
