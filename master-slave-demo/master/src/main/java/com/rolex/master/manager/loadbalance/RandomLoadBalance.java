package com.rolex.master.manager.loadbalance;

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
        Map<String, Channel> channels = executorManager.getChannels();
        Iterator<String> iterator = channels.keySet().iterator();
        String id = null;
        if (iterator.hasNext()) {
            id = iterator.next();
        }
        Channel channel = channels.get(id);
        return channel;
    }
}
