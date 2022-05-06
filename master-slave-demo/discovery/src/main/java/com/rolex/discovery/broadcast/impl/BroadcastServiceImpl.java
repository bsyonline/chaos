package com.rolex.discovery.broadcast.impl;

import com.alibaba.fastjson.JSONObject;
import com.rolex.discovery.broadcast.BroadcastService;
import com.rolex.discovery.broadcast.PubService;
import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.Metrics;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.discovery.util.NetUtils;
import com.rolex.discovery.util.OShiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
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
        log.info("{}", routingCache.getLocalRoutingInfo());
        RoutingInfo routingInfo = RoutingInfo.builder()
                .host(Host.of(NetUtils.getSiteIP(), getPort()))
                .type(NodeType.valueOf(type))
                .executorType(executorType)
                .connected(routingCache.getConnects().keySet())
                .metrics(Metrics.of(OShiUtils.getCpuLoad(), OShiUtils.getMemoryLoad()))
                .build();
        String broadcast = JSONObject.toJSONString(routingInfo);
        log.info("广播节点信息：{}", broadcast);
        pubService.pub(broadcast);
    }

    private int getPort() {
        if (NodeType.server == NodeType.valueOf(type)) {
            return port;
        } else {
            return routingCache.getLocalRoutingInfo().get("port") == null ? 0 : (int) routingCache.getLocalRoutingInfo().get("port");
        }
    }

}
