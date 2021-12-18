/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.factory.factorymethod;

import lombok.extern.slf4j.Slf4j;

/**
 * 工厂方法，新增类型不需要修改工厂类，但是类数量会增多
 *
 * @author rolex
 * @since 2020
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        Car benz = new Factory(new BenzFactory()).create();
        Car bmw = new Factory(new BmwFactory()).create();
        log.info("{}", benz);
        log.info("{}", bmw);
    }
}
