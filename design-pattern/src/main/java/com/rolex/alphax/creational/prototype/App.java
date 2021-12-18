/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.prototype;

import lombok.extern.slf4j.Slf4j;

/**
 * 深拷贝：拷贝值
 * 浅拷贝：拷贝引用
 * 原始类型 int long char 等会被 clone，String 不是原始类型，但是有 spring pool 机制，可以当成原始类型处理
 *
 * @author rolex
 * @since 2020
 */
@Slf4j
public class App {
    public static void main(String[] args) throws CloneNotSupportedException {
        LandRover landRover = new LandRover();
        landRover.setValue("路虎揽胜");
        LandWind landWind = new LandWind(landRover);
        LandRover car = (LandRover) landWind.create();
        car.setValue("陆风揽胜");
        log.info("{}", landRover.getValue());
    }
}
