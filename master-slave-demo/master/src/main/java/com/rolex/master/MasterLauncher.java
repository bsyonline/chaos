package com.rolex.master;

import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.util.NetUtils;
import com.rolex.master.manager.ExecutorManager;
import com.rolex.master.processor.RegistryProcessor;
import com.rolex.master.service.DispatchCoordinator;
import com.rolex.master.service.ExecutorService;
import com.rolex.rpc.NettyServer;
import com.rolex.rpc.model.proto.MsgProto;
import com.rolex.rpc.processor.impl.PingProcessor;
import com.rolex.rpc.processor.impl.ReceiveAckProcessor;
import com.rolex.rpc.processor.impl.ReceiveNackProcessor;
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
public class MasterLauncher implements CommandLineRunner {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(MasterLauncher.class).web(WebApplicationType.SERVLET).run(args);
    }

    @Value("${tcp.port}")
    int port;
    @Autowired
    RoutingCache routingCache;
    @Autowired
    ExecutorService executorService;
    @Autowired
    DispatchCoordinator dispatchCoordinator;

    @Override
    public void run(String... args) throws Exception {
        NettyServer nettyServer = new NettyServer(NetUtils.getHostName(), port);
        nettyServer.registerProcessor(MsgProto.CommandType.PING, new PingProcessor());
        nettyServer.registerProcessor(MsgProto.CommandType.ACK, new ReceiveAckProcessor());
        nettyServer.registerProcessor(MsgProto.CommandType.NACK, new ReceiveNackProcessor());
        nettyServer.registerProcessor(MsgProto.CommandType.EXECUTOR_REGISTRY, new RegistryProcessor(executorService, dispatchCoordinator, routingCache));
        nettyServer.setRoutingCache(routingCache);
        nettyServer.start();
    }
}
