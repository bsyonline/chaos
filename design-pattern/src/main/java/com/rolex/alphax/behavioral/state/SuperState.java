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

public class SuperState implements IMarioState {

    private Mario mario;

    public SuperState(Mario mario) {
        this.mario = mario;
    }

    @Override
    public String getState() {
        return "SUPER";
    }

    @Override
    public void getMushRoom() {
        log.info("超级马里奥吃了蘑菇，积分+100");
    }

    @Override
    public void meetMonster() {
        mario.setCurrentState(new SmallState(mario));
        log.info("超级马里奥遇到了怪物，变成了小马里奥");
    }
}
