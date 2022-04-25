package com.rolex.master.manager;

import com.rolex.discovery.routing.Host;
import com.rolex.master.manager.loadbalance.LoadBalanceStrategy;
import com.rolex.master.manager.loadbalance.LoadBalancer;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class DefaultExecutorManager implements ExecutorManager {

    private LoadBalanceStrategy loadBalanceStrategy;
    private LoadBalancer loadBalancer;
    private static Map<Host, Channel> channels = new ConcurrentHashMap<>();

    @Override
    public LoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

    @Override
    public Channel select() {
        return loadBalancer.select();
    }

    public void setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public static Channel getChannel(Host host) {
        return channels.get(host);
    }

    public LoadBalanceStrategy getLoadBalanceStrategy() {
        return loadBalanceStrategy;
    }

    public void setLoadBalanceStrategy(LoadBalanceStrategy loadBalanceStrategy) {
        this.loadBalanceStrategy = loadBalanceStrategy;
    }

    public static void setChannels(Map<Host, Channel> channels) {
        DefaultExecutorManager.channels = channels;
    }

    @Override
    public Map<Host, Channel> getChannels() {
        return channels;
    }

    @Override
    public void addChannel(Host host, Channel channel) {
        channels.put(host, channel);
    }

    @Override
    public void removeChannel(Host name) {
        channels.remove(name);
    }

}
