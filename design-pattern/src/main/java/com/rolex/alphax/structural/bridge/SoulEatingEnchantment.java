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
public class SoulEatingEnchantment implements Enchantment {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoulEatingEnchantment.class);

    @Override
    public void onActivate() {
        LOGGER.info("获得嗜血效果.");
    }

    @Override
    public void apply() {
        LOGGER.info("吞噬敌人灵魂.");
    }

    @Override
    public void onDeactivate() {
        LOGGER.info("嗜血效果消失.");
    }
}
