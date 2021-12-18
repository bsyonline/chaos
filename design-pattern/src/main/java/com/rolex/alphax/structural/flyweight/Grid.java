/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.flyweight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author rolex
 * @since 2020
 */
@Setter
@Getter
@AllArgsConstructor
public class Grid {
    int x;
    int y;
    Potion potion;

    @Override
    public String toString() {
        return "第" + (x + 1) + "行" + (y + 1) + "列：" + potion.remark();
    }
}
