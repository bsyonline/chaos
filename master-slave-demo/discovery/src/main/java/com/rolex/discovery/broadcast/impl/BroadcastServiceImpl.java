package com.rolex.discovery.broadcast.impl;

import com.alibaba.fastjson.JSONObject;
import com.rolex.discovery.broadcast.BroadcastService;
import com.rolex.discovery.broadcast.PubService;
import com.rolex.discovery.observer.RoutingInfoObserver;
import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.Metrics;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.discovery.util.NetUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
@Slf4j
public class BroadcastServiceImpl implements BroadcastService {

    @Value("${node.id}")
    int nodeId;
    @Value("${tcp.port:0}")
    int port;
    @Value("${node.type}")
    String type;
    @Value("${dts.executor.tenant:null}")
    String executorType;
    @Resource
    private PubService pubService;
    @Autowired
    RoutingCache routingCache;

    @Override
    public void broadcast() throws Exception {
        RoutingInfo routingInfo = RoutingInfo.builder()
                .host(Host.of(NetUtils.getHostName(), getPort()))
                .type(NodeType.valueOf(type))
                .executorType(executorType)
                .connected(routingCache.getConnects().keySet())
                .observer(new RoutingInfoObserver())
                .metrics(Metrics.of(getCpuLoadThreshold(), getMemoryInMBThreshold()))
                .build();
        String broadcast = JSONObject.toJSONString(routingInfo);
        log.info("广播节点信息：{}", broadcast);
        pubService.pub(broadcast);
    }

    private Double getMemoryInMBThreshold() {
        return routingCache.getLoadThresholdMap().get("memoryInMBThreshold") == null ? Metrics.MEMORY_IN_MB_THRESHOLD : routingCache.getLoadThresholdMap().get("memoryInMBThreshold");
    }

    private Double getCpuLoadThreshold() {
        return routingCache.getLoadThresholdMap().get("cpuLoadThreshold") == null ? Metrics.CPU_LOAD_THRESHOLD : routingCache.getLoadThresholdMap().get("cpuLoadThreshold");
    }

    private int getPort() {
        if (NodeType.server == NodeType.valueOf(type)) {
            return port;
        } else {
            return routingCache.getLocalRoutingInfo().get("port") == null ? 0 : (int) routingCache.getLocalRoutingInfo().get("port");
        }
    }

}
