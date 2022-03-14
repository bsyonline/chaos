package com.rolex.master.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rolex.common.model.RouteInfo;
import com.rolex.common.util.NetUtils;
import com.rolex.master.service.BroadcastService;
import com.rolex.master.service.PublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.SocketException;
import java.net.UnknownHostException;

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
    private PublisherService publisherService;

    @Override
    public void broadcast() throws Exception {
        String broadcast = JSONObject.toJSONString(new RouteInfo(nodeId, NetUtils.getSiteIP(), port, type, System.currentTimeMillis(), null));
        log.info("广播节点信息：{}", broadcast);
        publisherService.pushMsg(broadcast);
    }
}
