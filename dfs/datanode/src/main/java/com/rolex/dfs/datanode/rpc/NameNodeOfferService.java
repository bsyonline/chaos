package com.rolex.dfs.datanode.rpc;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <P>
 * 负责跟一组NameNode进行通信的OfferServie组件
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class NameNodeOfferService {

    /**
     * 负责跟NameNode主节点通信的ServiceActor组件
     */
    private NameNodeServiceActor serviceActor;
    /**
     * 这个datanode上保存的ServiceActor列表
     */
    private CopyOnWriteArrayList<NameNodeServiceActor> serviceActors;

    /**
     * 构造函数
     */
    public NameNodeOfferService() {
        this.serviceActor = new NameNodeServiceActor();
    }

    /**
     * 启动OfferService组件
     */
    public void start() {
        // 直接使用两个ServiceActor组件分别向主备两个NameNode节点进行注册
        register();
        // 开始发送心跳
        startHeartbeat();
    }

    /**
     * 向主备两个NameNode节点进行注册
     */
    public void register() {
        try {
            this.serviceActor.register();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向主备两个NameNode节点取消注册
     */
    public void unRegistry() {
        try {
            this.serviceActor.unRegistry();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始发送心跳给NameNode
     */
    public void startHeartbeat() {
        this.serviceActor.startHeartbeat();
    }

    /**
     * 关闭指定的一个ServiceActor
     */
    public void shutdown() {
        this.serviceActors.remove(serviceActor);
    }

    /**
     * 迭代遍历ServiceActor
     */
    public void iterateServiceActors() {
        Iterator<NameNodeServiceActor> iterator = serviceActors.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
    }
}
