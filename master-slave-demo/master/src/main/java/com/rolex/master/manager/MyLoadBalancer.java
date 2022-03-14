package com.rolex.master.manager;

import com.rolex.master.manager.loadbalance.RandomLoadBalance;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

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
@Component
public class MyLoadBalancer implements LoadBalancer {
    @Override
    public LoadBalanceStrategy select() {
        LoadBalanceStrategy loadBalanceStrategy = new RandomLoadBalance();
        return loadBalanceStrategy;
    }
}
