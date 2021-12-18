package com.rolex.dfs.namenode.service;

import com.rolex.dfs.namenode.fs.FSNameSystem;
import com.rolex.dfs.rpc.model.HeartbeatRequest;
import com.rolex.dfs.rpc.model.HeartbeatResponse;
import com.rolex.dfs.rpc.model.MkdirRequest;
import com.rolex.dfs.rpc.model.MkdirResponse;
import com.rolex.dfs.rpc.model.RegisterRequest;
import com.rolex.dfs.rpc.model.RegisterResponse;
import com.rolex.dfs.rpc.model.UnRegistryRequest;
import com.rolex.dfs.rpc.model.UnRegistryResponse;
import com.rolex.dfs.rpc.service.NameNodeServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class NameNodeServiceImpl extends NameNodeServiceGrpc.NameNodeServiceImplBase {
    public static final Integer STATUS_SUCCESS = 1;
    public static final Integer STATUS_FAILURE = 2;

    /**
     * 负责管理元数据的核心组件
     */
    private FSNameSystem nameSystem;
    /**
     * 负责管理集群中所有的datanode的组件
     */
    private DataNodeManager datanodeManager;

    public NameNodeServiceImpl(
            FSNameSystem nameSystem,
            DataNodeManager datanodeManager) {
        this.nameSystem = nameSystem;
        this.datanodeManager = datanodeManager;
    }

    /**
     * 创建目录
     * @return 是否创建成功
     * @throws Exception
     */
    @Override
    public void mkdir(MkdirRequest request,
                         StreamObserver<MkdirResponse> responseObserver) {
        try {
            this.nameSystem.mkdir(request.getPath());

            MkdirResponse response = MkdirResponse.newBuilder()
                    .setStatus(STATUS_SUCCESS)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * datanode进行注册
     * @return
     * @throws Exception
     */
    @Override
    public void register(RegisterRequest request,
                         StreamObserver<RegisterResponse> responseObserver) {
        datanodeManager.register(request.getIp(), request.getHostname());

        RegisterResponse response = RegisterResponse.newBuilder()
                .setStatus(STATUS_SUCCESS)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * datanode取消注册
     * @return
     * @throws Exception
     */
    @Override
    public void unRegistry(UnRegistryRequest request,
                           StreamObserver<UnRegistryResponse> responseObserver) {
        datanodeManager.unRegistry(request.getIp(), request.getHostname());

        UnRegistryResponse response = UnRegistryResponse.newBuilder()
                .setStatus(STATUS_SUCCESS)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * datanode进行心跳
     * @return
     * @throws Exception
     */
    @Override
    public void heartbeat(HeartbeatRequest request,
                          StreamObserver<HeartbeatResponse> responseObserver) {
        datanodeManager.heartbeat(request.getIp(), request.getHostname());

        HeartbeatResponse response = HeartbeatResponse.newBuilder()
                .setStatus(STATUS_SUCCESS)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
