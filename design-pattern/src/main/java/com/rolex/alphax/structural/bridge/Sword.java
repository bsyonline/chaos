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
public class Sword implements Weapon {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sword.class);

    private final Enchantment enchantment;

    public Sword(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public void wield() {
        LOGGER.info("持剑.");
        enchantment.onActivate();
    }

    @Override
    public void swing() {
        LOGGER.info("挥动剑.");
        enchantment.apply();
    }

    @Override
    public void unwield() {
        LOGGER.info("收起剑.");
        enchantment.onDeactivate();
    }

    @Override
    public Enchantment getEnchantment() {
        return enchantment;
    }
}
