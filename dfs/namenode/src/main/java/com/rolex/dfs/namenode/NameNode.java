package com.rolex.dfs.namenode;

import com.rolex.dfs.namenode.fs.FSNameSystem;
import com.rolex.dfs.namenode.rpc.NameNodeRpcServer;
import com.rolex.dfs.namenode.service.DataNodeManager;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class NameNode {

    /**
     * 负责管理元数据的核心组件：管理的是一些文件目录树，支持权限设置
     */
    private FSNameSystem nameSystem;
    /**
     * 负责管理集群中所有的Datanode的组件
     */
    private DataNodeManager datanodeManager;
    /**
     * NameNode对外提供rpc接口的server，可以响应请求
     */
    private NameNodeRpcServer rpcServer;

    /**
     * 初始化NameNode
     */
    private void initialize() throws Exception {
        this.nameSystem = new FSNameSystem();
        this.datanodeManager = new DataNodeManager();
        this.rpcServer = new NameNodeRpcServer(this.nameSystem, this.datanodeManager);
    }

    /**
     * 让NameNode运行起来
     */
    @PostConstruct
    public void run() throws Exception {
        initialize();
        start();
    }

    private void start() throws Exception {
        this.rpcServer.start();
        this.rpcServer.blockUntilShutdown();
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(NameNode.class).web(WebApplicationType.NONE).run(args);
    }
}
