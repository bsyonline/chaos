package com.rolex.master.service.impl;

import com.rolex.master.manager.ExecutorManager;
import com.rolex.master.manager.LoadBalanceStrategy;
import com.rolex.master.manager.LoadBalancer;
import com.rolex.master.service.DispatcherService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
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

    @Override
    public void dispatch(String msg) {
        LoadBalanceStrategy lb = loadBalancer.select();
        Channel channel = lb.getChannel(executorManager);
        if (!channel.isActive()) {
            executorManager.removeChannel(channel.id().asLongText());
        } else {
            ByteBuf buffer = Unpooled.buffer();
            //buffer.writeBytes((LocalDateTime.now() + " 服务端主动推送").getBytes());
            buffer.writeBytes(msg.getBytes());
            channel.writeAndFlush(buffer);
            log.info("发送成功，{}", msg);
        }
    }
}
