package com.rolex.dfs.client;

import com.rolex.dfs.rpc.model.MkdirRequest;
import com.rolex.dfs.rpc.model.MkdirResponse;
import com.rolex.dfs.rpc.service.NameNodeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
public class FileSystem {

    private static final String NAMENODE_HOSTNAME = "localhost";
    private static final Integer NAMENODE_PORT = 50070;
    private NameNodeServiceGrpc.NameNodeServiceBlockingStub nameNode;

    public FileSystem() {
        ManagedChannel channel = NettyChannelBuilder
                .forAddress(NAMENODE_HOSTNAME, NAMENODE_PORT)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();
        this.nameNode = NameNodeServiceGrpc.newBlockingStub(channel);
    }

    public void mkdir(String path) {
        MkdirRequest mkdirRequest = MkdirRequest.newBuilder().setPath(path).build();
        MkdirResponse mkdirResponse = nameNode.mkdir(mkdirRequest);
        log.info("创建目录的响应:{}", mkdirResponse);
    }
}
