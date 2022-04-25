package com.rolex.slave;

import com.rolex.discovery.routing.RoutingCache;
import com.rolex.rpc.CommandType;
import com.rolex.rpc.NettyClient;
import com.rolex.rpc.processor.impl.PongProcessor;
import com.rolex.rpc.rebalance.strategy.RandomSelector;
import com.rolex.slave.processor.JobAcceptProcessor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void run(String... args) throws Exception {
        NettyClient nettyClient = new NettyClient();
        nettyClient.registerProcessor(CommandType.PONG, new PongProcessor());
        nettyClient.registerProcessor(CommandType.JOB_REQUEST, new JobAcceptProcessor());
        nettyClient.setServerSelectorStrategy(new RandomSelector());
        nettyClient.setRoutingCache(routingCache);
        nettyClient.start();
    }
}
