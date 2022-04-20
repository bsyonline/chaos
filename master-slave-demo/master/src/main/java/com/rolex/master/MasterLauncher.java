package com.rolex.master;

import com.rolex.discovery.util.NetUtils;
import com.rolex.master.manager.ExecutorManager;
import com.rolex.rpc.CommandType;
import com.rolex.rpc.NettyServer;
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
@ComponentScan(value="com.rolex")
public class MasterLauncher implements CommandLineRunner{

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(MasterLauncher.class).web(WebApplicationType.SERVLET).run(args);
    }

    @Value("${tcp.port}")
    int port;
    @Autowired
    ExecutorManager executorManager;

    @Override
    public void run(String... args) throws Exception {
        NettyServer nettyServer = new NettyServer(NetUtils.getSiteIP(), port);
        nettyServer.registerProcessor(CommandType.PING, new PingProcessor());
        nettyServer.registerProcessor(CommandType.ACK, new ReceiveAckProcessor());
        nettyServer.registerProcessor(CommandType.NACK, new ReceiveNackProcessor());
        nettyServer.executorManager(executorManager);
        nettyServer.start();
    }
}
