package com.rolex.master.manager;

import com.rolex.discovery.routing.Host;
import com.rolex.master.manager.loadbalance.LoadBalancer;
import com.rolex.rpc.model.Manager;
import io.netty.channel.Channel;

import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface ExecutorManager extends Manager {

    @Override
    void addChannel(Host host, Channel channel);

    @Override
    void removeChannel(Host name);

    @Override
    Map<Host, Channel> getChannels();

    LoadBalancer getLoadBalancer();

    Channel select();

}
