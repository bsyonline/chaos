package com.rolex.slave.config;

import com.rolex.discovery.routing.RoutingCache;
import com.rolex.rpc.rebalance.RebalanceStrategy;
import com.rolex.rpc.rebalance.RebalanceStrategyEnum;
import com.rolex.rpc.rebalance.strategy.RandomRebalanceStrategy;
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
public class SlaveConfig {
    @Value("${slave.rebalance.strategy:random}")
    String rebalanceStrategy;

    @Autowired
    RoutingCache routingCache;

    @Bean
    public RebalanceStrategy rebalanceStrategy() {
        RebalanceStrategy rebalanceStrategy1;
        RebalanceStrategyEnum rebalanceStrategyEnum = RebalanceStrategyEnum.valueOf(rebalanceStrategy);
        switch (rebalanceStrategyEnum) {
            case random:
                rebalanceStrategy1 = new RandomRebalanceStrategy();
                break;
            default:
                rebalanceStrategy1 = new RandomRebalanceStrategy();
        }
        return rebalanceStrategy1;
    }
}
