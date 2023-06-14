package com.rolex.slave.runner;

import com.rolex.discovery.routing.RoutingCache;
import com.rolex.rpc.NettyClient;
import com.rolex.rpc.model.proto.MsgProto;
import com.rolex.rpc.processor.impl.PongProcessor;
import com.rolex.rpc.rebalance.RebalanceStrategy;
import com.rolex.slave.processor.JobAcceptProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
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
public class SlaveRunner implements CommandLineRunner {

    @Autowired
    RoutingCache routingCache;
    @Autowired
    RebalanceStrategy rebalanceStrategy;
    @Value("${dts.executor.tenant:system}")
    String executorType;
    @Value("${server.port}")
    int httpPort;

    @Override
    public void run(String... args) throws Exception {
        log.info("============runner");
        NettyClient nettyClient = new NettyClient();
        nettyClient.registerProcessor(MsgProto.CommandType.PONG, new PongProcessor());
        nettyClient.registerProcessor(MsgProto.CommandType.JOB_REQUEST, new JobAcceptProcessor());
        nettyClient.setServerSelectorStrategy(rebalanceStrategy);
        nettyClient.setExecutorType(executorType);
        nettyClient.setRoutingCache(routingCache);
        nettyClient.setHttpPort(httpPort);
        nettyClient.start();
    }
}
