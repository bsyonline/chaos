package com.rolex.dfs.namenode.rpc;

import com.rolex.dfs.namenode.service.DataNodeManager;
import com.rolex.dfs.namenode.service.NameNodeServiceImpl;
import com.rolex.dfs.namenode.fs.FSNameSystem;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.AllArgsConstructor;

import java.io.IOException;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@AllArgsConstructor
public class NameNodeRpcServer {

    private static final int DEFAULT_PORT = 50070;
    private Server server = null;
    /**
     * 负责管理元数据的核心组件
     */
    private FSNameSystem nameSystem;
    /**
     * 负责管理集群中所有的datanode的组件
     */
    private DataNodeManager datanodeManager;

    public NameNodeRpcServer(
            FSNameSystem nameSystem,
            DataNodeManager datanodeManager) {
        this.nameSystem = nameSystem;
        this.datanodeManager = datanodeManager;
    }

    public Boolean mkdir(String path) throws Exception {
        return this.nameSystem.mkdir(path);
    }

    public void start() throws IOException {
        // 启动一个rpc server，监听指定的端口号
        // 同时绑定好了自己开发的接口
        server = ServerBuilder
                .forPort(DEFAULT_PORT)
                .addService(new NameNodeServiceImpl(nameSystem, datanodeManager))
                .build()
                .start();

        System.out.println("NameNodeRpcServer启动，监听端口号：" + DEFAULT_PORT);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                NameNodeRpcServer.this.stop();
            }
        });
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

}
