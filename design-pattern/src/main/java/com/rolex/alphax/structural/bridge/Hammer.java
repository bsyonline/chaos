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
public class Hammer implements Weapon {

    private static final Logger LOGGER = LoggerFactory.getLogger(Hammer.class);

    private final Enchantment enchantment;

    public Hammer(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public void wield() {
        LOGGER.info("握锤子.");
        enchantment.onActivate();
    }

    @Override
    public void swing() {
        LOGGER.info("挥动锤子.");
        enchantment.apply();
    }

    @Override
    public void unwield() {
        LOGGER.info("收起锤子.");
        enchantment.onDeactivate();
    }

    @Override
    public Enchantment getEnchantment() {
        return enchantment;
    }
}
