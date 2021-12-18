/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.builder;

import java.util.Arrays;

/**
 * @author rolex
 * @since 2020
 */
public class Hero {
    private Hero() {
    }

    private String name;
    private Profession profession;
    private Armor armor;
    private Weapon weapon;
    private Mount mount;
    private int level;

    public Hero(Builder builder) {
        this.name = builder.name;
        this.profession = builder.profession;
        this.armor = builder.armor;
        this.weapon = builder.weapon;
        this.level = builder.level;
        this.mount = builder.mount;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "英雄：'" + name + '\'' +
                ", 职业：'" + profession + '\'' +
                ", 等级：" + level +
                ", 盔甲：'" + (armor == null ? "" : armor.getName()) + '\'' +
                ", 武器：'" + (weapon == null ? "" : weapon.getName()) + '\'' +
                ", 坐骑：'" + (mount == null ? "" : mount.getName()) + '\'' +
                '}';
    }

    public static class Builder {
        private String name;
        private Profession profession;
        private Armor armor;
        private Weapon weapon;
        private int level;
        private Mount mount;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder profession(Profession profession) {
            this.profession = profession;
            return this;
        }

        public Builder armor(Armor armor) {
            if (level >= armor.level && profession.point >= armor.category.point) {
                this.armor = armor;
            }
            return this;
        }

        public Builder weapon(Weapon weapon) {
            if (level >= weapon.level) {
                this.weapon = weapon;
            }
            return this;
        }

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public Builder mount(Mount mount) {
            if (level >= 40 && level >= mount.level && Arrays.asList(mount.profession).contains(profession)) {
                this.mount = mount;
            }
            return this;
        }

        public Hero build() {
            return new Hero(this);
        }
    }
}
