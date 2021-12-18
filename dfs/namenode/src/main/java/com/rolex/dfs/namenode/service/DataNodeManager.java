package com.rolex.dfs.namenode.service;

import com.rolex.dfs.namenode.model.DataNodeInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class DataNodeManager {
    /**
     * 集群中所有的datanode
     */
    private Map<String, DataNodeInfo> dataNodes = new ConcurrentHashMap<String, DataNodeInfo>();

    public DataNodeManager() {
        new DataNodeAliveMonitor().start();
    }

    /**
     * datanode进行注册
     *
     * @param ip
     * @param hostname
     */
    public Boolean register(String ip, String hostname) {
        DataNodeInfo datanode = new DataNodeInfo(ip, hostname);
        dataNodes.put(ip + "-" + hostname, datanode);
        System.out.println("DataNode注册：ip=" + ip + ",hostname=" + hostname);
        return true;
    }

    /**
     * datanode取消注册
     *
     * @param ip
     * @param hostname
     */
    public Boolean unRegistry(String ip, String hostname) {
        DataNodeInfo datanode = new DataNodeInfo(ip, hostname);
        dataNodes.remove(ip + "-" + hostname, datanode);
        System.out.println("DataNode取消注册：ip=" + ip + ",hostname=" + hostname);
        return true;
    }

    /**
     * datanode进行心跳
     *
     * @param ip
     * @param hostname
     * @return
     */
    public Boolean heartbeat(String ip, String hostname) {
        DataNodeInfo datanode = dataNodes.get(ip + "-" + hostname);
        datanode.setLatestHeartbeatTime(System.currentTimeMillis());
        System.out.println("DataNode发送心跳：ip=" + ip + ",hostname=" + hostname);
        return true;
    }

    /**
     * datanode是否存活的监控线程
     *
     */
    class DataNodeAliveMonitor extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    List<String> toRemoveDatanodes = new ArrayList<String>();

                    Iterator<DataNodeInfo> datanodesIterator = dataNodes.values().iterator();
                    DataNodeInfo datanode = null;
                    while (datanodesIterator.hasNext()) {
                        datanode = datanodesIterator.next();
                        if (System.currentTimeMillis() - datanode.getLatestHeartbeatTime() > 90 * 1000) {
                            toRemoveDatanodes.add(datanode.getIp() + "-" + datanode.getHostname());
                        }
                    }

                    if (!toRemoveDatanodes.isEmpty()) {
                        for (String toRemoveDatanode : toRemoveDatanodes) {
                            dataNodes.remove(toRemoveDatanode);
                        }
                    }

                    Thread.sleep(30 * 1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
