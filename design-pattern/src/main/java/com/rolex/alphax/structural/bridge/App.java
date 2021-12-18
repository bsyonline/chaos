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
public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("骑士获得附魔长剑.");
        Weapon enchantedSword = new Sword(new SoulEatingEnchantment());
        enchantedSword.wield();
        enchantedSword.swing();
        enchantedSword.unwield();

        LOGGER.info("女武神获得附魔锤子.");
        Weapon hammer = new Hammer(new FlyingEnchantment());
        hammer.wield();
        hammer.swing();
        hammer.unwield();
    }
}
