package com.rolex.master.manager.loadbalance;

import com.rolex.master.manager.ExecutorManager;
import com.rolex.master.manager.LoadBalanceStrategy;
import io.netty.channel.Channel;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class RoundRobinLoadBalance implements LoadBalanceStrategy {
    @Override
    public Channel getChannel(ExecutorManager executorManager) {
        return null;
    }
}
