/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.state;

import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class SmallState implements IMarioState {

    private Mario mario;

    public SmallState(Mario mario) {
        this.mario = mario;
    }

    @Override
    public String getState() {
        return "SMALL";
    }

    @Override
    public void getMushRoom() {
        mario.setCurrentState(new SuperState(mario));
        log.info("小马里奥吃了蘑菇，变成了超级马里奥");
    }

    @Override
    public void meetMonster() {
        log.info("小马里奥遇到了怪物，游戏结束");
    }
}
