/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.rpc.provider;

import com.rolex.alphax.rpc.netty.NettyServer;

/**
 * @author rolex
 * @since 2020
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("localhost", 7000);
    }
}
