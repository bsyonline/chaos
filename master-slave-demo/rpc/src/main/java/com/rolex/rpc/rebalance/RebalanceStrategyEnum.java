package com.rolex.rpc.rebalance;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public enum RebalanceStrategyEnum {
    random,
    circle,
    config,
    consistentHash,
    exclude,
    group,
    nearby,
    weight
    ;
}
