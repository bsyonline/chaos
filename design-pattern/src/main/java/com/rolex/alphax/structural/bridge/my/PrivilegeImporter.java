/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.bridge.my;

/**
 * @author rolex
 * @since 2020
 */
public class PrivilegeImporter implements Importer{

    Strategy strategy;

    public PrivilegeImporter(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void parse() {

    }

    @Override
    public void transfer() {

    }

    @Override
    public void doImport() {

    }

    @Override
    public Strategy getStrategy() {
        return strategy;
    }
}
