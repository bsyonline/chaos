package com.rolex.slave;

import com.rolex.discovery.routing.RoutingCache;
import com.rolex.rpc.CommandType;
import com.rolex.rpc.NettyClient;
import com.rolex.rpc.model.proto.MsgProto;
import com.rolex.rpc.processor.impl.PongProcessor;
import com.rolex.rpc.rebalance.RebalanceStrategy;
import com.rolex.rpc.rebalance.strategy.RandomRebalanceStrategy;
import com.rolex.slave.processor.JobAcceptProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@SpringBootApplication
@ComponentScan(value = "com.rolex")
public class SlaveLauncher implements CommandLineRunner {
    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(SlaveLauncher.class).web(WebApplicationType.NONE).run(args);
    }

    @Autowired
    RoutingCache routingCache;
    @Autowired
    RebalanceStrategy rebalanceStrategy;
    @Value("${dts.executor.tenant:null}")
    String executorType;

    @Override
    public void run(String... args) throws Exception {
        NettyClient nettyClient = new NettyClient();
        nettyClient.registerProcessor(MsgProto.CommandType.PONG, new PongProcessor());
        nettyClient.registerProcessor(MsgProto.CommandType.JOB_REQUEST, new JobAcceptProcessor());
        nettyClient.setServerSelectorStrategy(rebalanceStrategy);
        nettyClient.setExecutorType(executorType);
        nettyClient.setRoutingCache(routingCache);
        nettyClient.start();
    }
}
