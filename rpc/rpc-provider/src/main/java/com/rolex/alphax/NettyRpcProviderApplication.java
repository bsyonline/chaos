package com.rolex.alphax;

import com.rolex.alphax.netty.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyRpcProviderApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcProviderApplication.class, args);
    }

    @Autowired
    NettyServer nettyServer;

    @Override
    public void run(String... args) throws Exception {
        nettyServer.startServer(8000);
    }
}
