/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;

/**
 * @author rolex
 * @since 2020
 */
public class InitializationExample3 {
    static class DeadLoopClass {
        static {
            if (true) {
                System.out.println(Thread.currentThread() + " init DeadLoopClass");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Runnable script = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread() + " start");
                DeadLoopClass dlc = new DeadLoopClass();
                System.out.println(Thread.currentThread() + " run over");
            }
        };
        /*
            同时只能有一个线程去初始化DeadLoopClass，在初始化过程中，其他线程会阻塞直到初始化结束
         */
        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();
    }

}

