/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.flyweight;

/**
 * @author rolex
 * @since 2020
 */
public class ManaPotion implements Potion {
    @Override
    public String remark() {
        return "法力药水-" + System.identityHashCode(this);
    }
}
