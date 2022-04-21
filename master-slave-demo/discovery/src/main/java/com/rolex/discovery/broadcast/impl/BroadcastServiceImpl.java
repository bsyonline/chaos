package com.rolex.discovery.broadcast.impl;

import com.alibaba.fastjson.JSONObject;
import com.rolex.discovery.broadcast.BroadcastService;
import com.rolex.discovery.broadcast.PubService;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.discovery.util.NetUtils;
import lombok.extern.slf4j.Slf4j;
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
    @Value("${tcp.port}")
    int port;
    @Value("${node.type}")
    String type;
    @Resource
    private PubService pubService;

    @Override
    public void broadcast() throws Exception {
        String broadcast = JSONObject.toJSONString(new RoutingInfo(nodeId, NetUtils.getSiteIP(), port, NodeType.valueOf(type), System.currentTimeMillis(), null));
        log.info("广播节点信息：{}", broadcast);
        pubService.pub(broadcast);
    }
}
