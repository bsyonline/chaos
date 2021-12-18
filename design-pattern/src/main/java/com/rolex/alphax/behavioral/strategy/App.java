/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        StrategyFactory strategyFactory = new StrategyFactory();
        RateStrategy strategy = strategyFactory.getStrategy("blackcard");
        log.info("{}", strategy.fee(100));
        strategy = strategyFactory.getStrategy("goldcard");
        log.info("{}", strategy.fee(100));
        strategy = strategyFactory.getStrategy("silvercard");
        log.info("{}", strategy.fee(100));
    }
}
