/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.bridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rolex
 * @since 2020
 */
public class FlyingEnchantment implements Enchantment {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlyingEnchantment.class);

    @Override
    public void onActivate() {
        LOGGER.info("获得发光效果.");
    }

    @Override
    public void apply() {
        LOGGER.info("击中敌人.");
    }

    @Override
    public void onDeactivate() {
        LOGGER.info("发光效果消失.");
    }
}
