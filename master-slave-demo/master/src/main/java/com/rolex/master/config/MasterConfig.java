package com.rolex.master.config;

import com.rolex.discovery.routing.RoutingCache;
import com.rolex.master.manager.DefaultExecutorManager;
import com.rolex.master.manager.ExecutorManager;
import com.rolex.master.manager.loadbalance.LoadBalanceStrategyEnum;
import com.rolex.master.manager.loadbalance.LoadBalancer;
import com.rolex.master.manager.loadbalance.MasterLoadBalancer;
import com.rolex.master.manager.loadbalance.strategy.RandomLoadBalanceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Configuration
public class MasterConfig {
    @Value("${master.executor.loadBalance.strategy:random}")
    String loadBalanceStrategy;

    @Autowired
    RoutingCache routingCache;

    @Bean
    public ExecutorManager executorManager() {
        DefaultExecutorManager executorManager = new DefaultExecutorManager();
        LoadBalancer loadBalancer;

        LoadBalanceStrategyEnum loadBalanceStrategyEnum = LoadBalanceStrategyEnum.valueOf(loadBalanceStrategy);
        switch (loadBalanceStrategyEnum) {
            case random:
                loadBalancer = new MasterLoadBalancer(routingCache, new RandomLoadBalanceStrategy());
                break;
            default:
                loadBalancer = new MasterLoadBalancer(routingCache, new RandomLoadBalanceStrategy());
        }
        executorManager.setLoadBalancer(loadBalancer);
        return executorManager;
    }
}
