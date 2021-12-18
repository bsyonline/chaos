/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.bridge;

/**
 * @author rolex
 * @since 2020
 */
public interface Weapon {
    void wield();

    void swing();

    void unwield();

    Enchantment getEnchantment();
}
