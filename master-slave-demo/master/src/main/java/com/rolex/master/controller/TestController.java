package com.rolex.master.controller;

import com.rolex.discovery.routing.Host;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.discovery.routing.RoutingInfo;
import com.rolex.master.manager.PickerThreadManager;
import com.rolex.master.manager.TenantManager;
import com.rolex.master.model.Executor;
import com.rolex.master.service.DispatchCoordinator;
import com.rolex.master.service.DispatcherService;
import com.rolex.master.service.ExecutorService;
import com.rolex.rpc.model.proto.MsgProto;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@RestController
public class TestController {
    @Autowired
    DispatcherService dispatcherService;
    @Autowired
    RoutingCache routingCache;
    @Autowired
    ExecutorService executorService;
    @Autowired
    DispatchCoordinator dispatchCoordinator;

    @GetMapping("/test/{msg}")
    public String test(@PathVariable("msg") String msg) throws InterruptedException {
        dispatcherService.dispatch(msg);
        return "OK";
    }

    @GetMapping("/test/ready")
    public String ready() {
        new PickerThreadManager(executorService, dispatchCoordinator).create(getKeyPaths());
        return "OK";
    }

    @GetMapping("/test/cat")
    public Map cat() throws InterruptedException {
        Map<NodeType, Map<Host, RoutingInfo>> routingInfo = routingCache.getRoutingInfo();
        Map<Host, Channel> connects = routingCache.getConnects();
        Map<String, Object> localRoutingInfo = routingCache.getLocalRoutingInfo();
        Map map = new HashMap();
        Map<Host, RoutingInfo> client = routingInfo.get(NodeType.client);
        Set<Map.Entry<Host, RoutingInfo>> entries = client.entrySet();
        Map m1 = new HashMap();
        for (Map.Entry<Host, RoutingInfo> entry : entries) {
            RoutingInfo value = entry.getValue();
            m1.put("host", value.getHost());
            m1.put("connected", value.getConnected());
            m1.put("executorType", value.getExecutorType());
            m1.put("metrics", value.getMetrics());
            m1.put("type", value.getType());
            m1.put("timestamp", value.getTimestamp());
        }
        Map<Host, RoutingInfo> server = routingInfo.get(NodeType.server);
        Set<Map.Entry<Host, RoutingInfo>> entries1 = server.entrySet();
        Map m2 = new HashMap();
        for (Map.Entry<Host, RoutingInfo> entry : entries1) {
            RoutingInfo value = entry.getValue();
            m2.put("host", value.getHost());
            m2.put("connected", value.getConnected());
            m2.put("executorType", value.getExecutorType());
            m2.put("metrics", value.getMetrics());
            m2.put("type", value.getType());
            m2.put("timestamp", value.getTimestamp());
        }

        map.put("client", m1);
        map.put("server", m2);
        map.put("connects", connects);
        map.put("localRoutingInfo", localRoutingInfo);
        return map;
    }

    private Set<String> getKeyPaths() {
        Set<String> keySet = new HashSet<>();
        for (Host host : routingCache.getConnects().keySet()) {
            Executor executor = executorService.findByIpPort(host.getHost(), host.getPort());
            for (String tenant : TenantManager.getTenants()) {
                if (Objects.equals(executor.getTenantCode(), "system")) {
                    keySet.add(tenant + "_" + MsgProto.ExecutorType.system.getNumber());
                } else {
                    keySet.add(tenant + "_" + executor.getExecutorGroupId());
                }
            }
        }
        return keySet;
    }
}
