package com.rolex.slave.processor;

import com.alibaba.fastjson.JSONObject;
import com.rolex.rpc.CommandType;
import com.rolex.rpc.model.Msg;
import com.rolex.rpc.model.MsgBody;
import com.rolex.rpc.processor.NettyRequestProcessor;
import com.rolex.rpc.util.SerializationUtils;
import com.rolex.slave.exec.MainReactor;
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
public class JobAcceptProcessor implements NettyRequestProcessor {
    @Override
    public void process(Channel channel, MsgBody msg) throws InterruptedException {
        log.info("收到派发的作业 {} 准备接收", JSONObject.toJSONString(msg));
        boolean accept = MainReactor.accept("" + msg.getJobId());
        Thread.sleep(100000);
        log.info("收到派发的作业 {} 接收状态", JSONObject.toJSONString(msg), accept);
        if (accept) {
            log.info("收到派发的作业 {} 并返回ack", JSONObject.toJSONString(msg));
            ack(channel, msg);
        } else {
            log.info("收到派发的作业 {} ，当前任务积压，拒绝接收新任务并返回nack", JSONObject.toJSONString(msg));
            nack(channel, msg);
        }
    }

    private void ack(Channel channel, MsgBody msgBody) throws InterruptedException {
        MsgBody request = new MsgBody();
        request.setType(CommandType.ACK);
        request.setJobId(msgBody.getJobId());
        byte[] content = SerializationUtils.serialize(request, MsgBody.class);
        Msg msg = new Msg(content.length, content);
        channel.writeAndFlush(msg).sync();
    }

    private void nack(Channel channel, MsgBody msgBody) throws InterruptedException {
        MsgBody request = new MsgBody();
        request.setType(CommandType.NACK);
        request.setJobId(msgBody.getJobId());
        byte[] content = SerializationUtils.serialize(request, MsgBody.class);
        Msg msg = new Msg(content.length, content);
        channel.writeAndFlush(msg).sync();
    }
}
