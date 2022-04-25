package com.rolex.master.service.impl;

import com.rolex.master.manager.ExecutorManager;
import com.rolex.master.manager.LoadBalanceStrategy;
import com.rolex.master.manager.LoadBalancer;
import com.rolex.master.service.DispatcherService;
import com.rolex.rpc.CommandType;
import com.rolex.rpc.exception.RpcTimeoutException;
import com.rolex.rpc.future.ResponseFuture;
import com.rolex.rpc.model.Msg;
import com.rolex.rpc.model.MsgBody;
import com.rolex.rpc.util.SerializationUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
@Slf4j
public class DispatcherServiceImpl implements DispatcherService {

    @Autowired
    LoadBalancer loadBalancer;
    @Autowired
    ExecutorManager executorManager;
    Long count = 1001L;

    @Override
    public void dispatch(String msg) throws InterruptedException {
        LoadBalanceStrategy lb = loadBalancer.select();
        Channel channel = lb.getChannel(executorManager);
        if (channel != null) {
            Long jobId = count++;
            log.info("send before {}", jobId);
            send(channel, jobId);
            log.info("send after {}", jobId);
        }
    }

    private void send(Channel channel, Long jobId) throws InterruptedException {
        MsgBody request = new MsgBody();
        request.setType(CommandType.JOB_REQUEST);
        request.setJobId(jobId);
        byte[] content = SerializationUtils.serialize(request, MsgBody.class);
        Msg msg = new Msg(content.length, content);
        ChannelFuture future = channel.writeAndFlush(msg).await();
        if (future.isSuccess()) {
            log.debug("send command : {} , to : {} successfully.", msg, channel.remoteAddress().toString());
        } else {
            String error = String.format("send command : %s , to :%s failed", msg, channel.remoteAddress().toString());
            log.error(error, future.cause());
            throw new RuntimeException(error);
        }
    }

    private void sendSync(Channel channel, Long jobId) throws InterruptedException {
        MsgBody request = new MsgBody();
        request.setType(CommandType.JOB_REQUEST);
        request.setJobId(jobId);
        byte[] content = SerializationUtils.serialize(request, MsgBody.class);
        final ResponseFuture responseFuture = new ResponseFuture(15000);
        Msg msg = new Msg(content.length, content);
        ChannelFuture channelFuture = channel.writeAndFlush(msg).addListener(future -> {
            if (future.isSuccess()) {
                log.info("send ok");
                responseFuture.setSendOk(true);
                return;
            } else {
                log.info("send not ok");
                responseFuture.setSendOk(false);
            }
            responseFuture.setCause(future.cause());
            responseFuture.putResponse(null);
            log.error("send command {} to host {} failed", msg, channel.remoteAddress().toString());
        });

        Msg result = responseFuture.waitResponse();
        if (result == null) {
            if (responseFuture.isSendOK()) {
                throw new RpcTimeoutException(channel.remoteAddress().toString(), responseFuture.getCause(), 15000);
            } else {
                throw new RuntimeException(channel.remoteAddress().toString(), responseFuture.getCause());
            }
        }
    }

}
