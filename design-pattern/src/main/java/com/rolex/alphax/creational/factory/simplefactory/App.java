/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.factory.simplefactory;

import lombok.extern.slf4j.Slf4j;

/**
 * 简单工厂，新增类需要修改工厂类
 *
 * @author rolex
 * @since 2020
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        Car benz = CarFactory.create("benz");
        Car bmw = CarFactory.create("bmw");
        log.info("this car is {}", benz);
        log.info("this car is {}", bmw);
    }
}
