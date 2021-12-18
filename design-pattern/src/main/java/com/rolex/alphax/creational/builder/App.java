/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.builder;

import lombok.extern.slf4j.Slf4j;

import static com.rolex.alphax.creational.builder.Armor.Category.Cloth;
import static com.rolex.alphax.creational.builder.Armor.Category.Plate;
import static com.rolex.alphax.creational.builder.Profession.Hunter;
import static com.rolex.alphax.creational.builder.Profession.Mage;
import static com.rolex.alphax.creational.builder.Profession.Paladin;
import static com.rolex.alphax.creational.builder.Profession.Warrior;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        Hero hero = new Hero.Builder()
                .name("阿尔萨斯")
                .profession(Paladin)
                .level(0)
                .armor(new Armor("秩序之源", 60, Paladin, Plate))
                .weapon(new Weapon("霜之哀伤", 90, new Profession[]{Warrior, Paladin}))
                .mount(new Mount("军马", 60, new Profession[]{Paladin}))
                .build();
        log.info(hero.toString());
        Hero hero1 = new Hero.Builder()
                .name("吉安娜")
                .profession(Mage)
                .level(90)
                .armor(new Armor("霜火", 60, Mage, Cloth))
                .weapon(new Weapon("埃提耶什，守护者的传说之杖", 90, new Profession[]{Mage}))
                .mount(new Mount("午夜", 60, new Profession[]{Warrior, Paladin, Mage, Hunter}))
                .build();
        log.info(hero1.toString());
    }
}
