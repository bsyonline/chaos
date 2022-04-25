package com.rolex.master.manager.loadbalance;

import com.rolex.discovery.routing.Host;
import com.rolex.master.manager.ExecutorManager;
import com.rolex.master.manager.LoadBalanceStrategy;
import io.netty.channel.Channel;

import java.util.Iterator;
import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class RandomLoadBalance implements LoadBalanceStrategy {

    @Override
    public Channel getChannel(ExecutorManager executorManager) {
        Map<Host, Channel> channels = executorManager.getChannels();
        Iterator<Host> iterator = channels.keySet().iterator();
        Host host = null;
        if (iterator.hasNext()) {
            host = iterator.next();
        }
        Channel channel = channels.get(host);
        if (channel.isActive()) {
            return channel;
        }
        channels.remove(host);
        return null;
    }
}
