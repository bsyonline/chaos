/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.thread;

/**
 * @author rolex
 * @since 2020
 */
public class VolatileExample {
    volatile boolean init = false;

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileExample volatileExample = new VolatileExample();
        new Thread(() -> {
            while (!volatileExample.isInit()) {
            }
            System.out.println("t2 completed");
        }, "t2").start();

        Thread.sleep(5000);
        volatileExample.setInit(true);
        System.out.println("init = true");
    }
}
