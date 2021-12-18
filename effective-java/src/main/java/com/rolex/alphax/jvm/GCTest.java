package com.rolex.alphax.jvm;

import java.util.ArrayList;
import java.util.List;

public class GCTest {
    public static void main(String[] args) throws Exception {
//        List caches = new ArrayList();
//        for (int i = 0; i < 7; i++) {
//            System.out.println("次数：" + (i + 1) +" 剩余内存："+ Runtime.getRuntime().freeMemory()/1024/1024);
//            caches.add(new byte[1024 * 1024 * 3]);
//        }
//        caches.clear();
//        for (int i = 0; i < 2; i++) {
//            System.out.println("clear后次数：" + (i + 1));
//            caches.add(new byte[1024 * 1024 * 3]);
//        }
        List caches = new ArrayList();

        caches.add(new byte[1024 * 1024 * 10]);
        System.out.println("次数：" + (1) +" 剩余内存："+ Runtime.getRuntime().freeMemory()/1024/1024);
        caches.add(new byte[1024 * 1024 * 10]);
        caches.clear();
        System.out.println("次数：" + (2) +" 剩余内存："+ Runtime.getRuntime().freeMemory()/1024/1024);
        caches.add(new byte[1024 * 1024 * 10]);
        System.out.println("次数：" + (3) +" 剩余内存："+ Runtime.getRuntime().freeMemory()/1024/1024);
        caches.add(new byte[1024 * 1024 * 10]);
        System.out.println("次数：" + (4) +" 剩余内存："+ Runtime.getRuntime().freeMemory()/1024/1024);
        caches.clear();
        caches.add(new byte[1024 * 1024 * 10]);
        System.out.println("次数：" + (5) +" 剩余内存："+ Runtime.getRuntime().freeMemory()/1024/1024);
//        caches.add(new byte[1024 * 1024 * 3]);
//        System.out.println("次数：" + (2) +" 剩余内存："+ Runtime.getRuntime().freeMemory()/1024/1024);
//        caches.add(new byte[1024 * 1024 * 3]);
//        System.out.println("次数：" + (3) +" 剩余内存："+ Runtime.getRuntime().freeMemory()/1024/1024);
//        caches.add(new byte[1024 * 1024 * 3]);
//        System.out.println("次数：" + (4) +" 剩余内存："+ Runtime.getRuntime().freeMemory()/1024/1024);
//        caches.add(new byte[1024 * 1024 * 3]);
//        System.out.println("次数：" + (5) +" 剩余内存："+ Runtime.getRuntime().freeMemory()/1024/1024);
//        caches.add(new byte[1024 * 1024 * 3]);
//        System.out.println("次数：" + (6) +" 剩余内存："+ Runtime.getRuntime().freeMemory()/1024/1024);
//        caches.add(new byte[1024 * 1024 * 3]);
//        System.out.println("次数：" + (7) +" 剩余内存："+ Runtime.getRuntime().freeMemory()/1024/1024);
    }
}