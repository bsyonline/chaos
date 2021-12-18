/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;

/**
 * 1. java -XX:+PrintCommandLineFlags -version
 * 2. java -XX:+PrintFlagsInitial -version
 * 3. java -XX:+PrintFlagsFinal -version
 * 4. jinfo -flag
 *
 * @author rolex
 * @since 2020
 */
public class RuntimeExample {
    public static void main(String[] args) {
        System.out.println("CPU可用核数：" + Runtime.getRuntime().availableProcessors());//可用核数
        System.out.println("JVM最大内存：" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB");//最大内存
        System.out.println("JVM可用内存：" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "MB");//剩余内存
        System.out.println("JVM总内存：" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB");//剩余内存

//        try {
//            Thread.sleep(Integer.MAX_VALUE);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
