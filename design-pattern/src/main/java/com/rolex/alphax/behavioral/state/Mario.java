/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.state;

import lombok.Getter;
import lombok.Setter;

/**
 * @author rolex
 * @since 2020
 */
@Setter
@Getter
public class Mario {
    private IMarioState currentState;

    public Mario() {
        this.currentState = new SmallState(this);
    }

    public Mario(IMarioState currentState) {
        this.currentState = currentState;
    }

    public String getState(){
        return currentState.getState();
    }

    public void getMushRoom(){
        currentState.getMushRoom();
    }

    public void meetMonster(){
        currentState.meetMonster();
    }
}
