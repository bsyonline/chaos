/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.flyweight;

import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class HealthPotion implements Potion {
    @Override
    public String remark() {
        return "治疗药水-" + System.identityHashCode(this);
    }
}
