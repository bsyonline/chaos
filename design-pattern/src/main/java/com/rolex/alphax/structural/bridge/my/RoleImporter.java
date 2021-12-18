/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.bridge.my;

/**
 * @author rolex
 * @since 2020
 */
public class RoleImporter implements Importer {

    Strategy strategy;

    public RoleImporter(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void parse() {
        System.out.println("解析excel");
    }

    @Override
    public void transfer() {
        System.out.println("数据转换");
    }

    @Override
    public void doImport() {
        System.out.println("导入");
        strategy.override();
    }

    @Override
    public Strategy getStrategy() {
        return strategy;
    }
}
