/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.bridge.my;

/**
 * @author rolex
 * @since 2020
 */
public class IgnoreStrategy implements Strategy {
    @Override
    public void ignore() {
        System.out.println("忽略");
    }

    @Override
    public void create() {
        System.out.println("新建");
    }

    @Override
    public void override() {
        System.out.println("覆盖");
    }
}
