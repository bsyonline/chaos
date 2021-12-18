/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.atomic;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;

/**
 * @author rolex
 * @since 2020
 */
public class LongAccumulatorExample {
    public static void main(String[] args) throws InterruptedException {
        LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                // 指定计算规则
                return left + right;
            }
        }, 20);// 指定初值

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                longAccumulator.accumulate(1);
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                longAccumulator.accumulate(1);
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                longAccumulator.accumulate(1);
            }
        }).start();
        Thread.sleep(1000);
        System.out.println(longAccumulator.longValue());
    }
}
