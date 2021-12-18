/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.timewheel;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author rolex
 * @since 2020
 */
public class TimeWheel {
    WheelBucket[] wheel;
    private final AtomicBoolean stop = new AtomicBoolean(false);
    Queue<Integer> taskQueue = new LinkedBlockingQueue();

    public TimeWheel(int bucketSize) throws InterruptedException {
        wheel = new WheelBucket[bucketSize];
        for (int i = 0; i < bucketSize; i++) {
            wheel[i] = new WheelBucket();
        }
        long startTime = System.currentTimeMillis();
        new Thread(() -> {
            while (!stop.get()) {
                try {
                    Integer job = taskQueue.poll();
                    if(job!=null) {
                        System.out.println(job);
                        long offset = System.currentTimeMillis() - startTime;
                        long ticks = offset % 10;
                        wheel[(int) ticks].task.add(job);
                    }
                    TimeUnit.MICROSECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(System.currentTimeMillis() - startTime);
        },"ticks-scheduler").start();

    }

    public void show() {
        for (WheelBucket wheelBucket : wheel) {
            System.out.println("[" + wheelBucket.toString() + "],");
        }
        System.out.println();
    }

    public void createJob(Integer job) {
        taskQueue.add(job);
    }

    class WheelBucket {
        List<Integer> task = new ArrayList();

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            for (Integer job : task) {
                sb.append(job).append(",");
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TimeWheel timeWheel = new TimeWheel(10);
        for (int i = 1; i < 51; i++) {
            timeWheel.createJob(i);
            System.out.println(i);
        }
        TimeUnit.SECONDS.sleep(15);
        timeWheel.stop.set(true);

        timeWheel.show();
    }
}
