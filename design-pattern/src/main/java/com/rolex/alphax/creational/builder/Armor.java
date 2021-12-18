/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.builder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author rolex
 * @since 2020
 */
@AllArgsConstructor
@Setter
@Getter
public class Armor {
    String name;
    int level;
    Profession profession;
    Category category;

    enum Category {
        Plate(10), Mail(7), Leather(5), Cloth(3);
        int point;

        Category(int point) {
            this.point = point;
        }
    }
}
