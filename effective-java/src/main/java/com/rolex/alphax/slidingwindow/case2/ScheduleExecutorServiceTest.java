/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.slidingwindow.case2;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author rolex
 * @since 2020
 */
public class ScheduleExecutorServiceTest {
    public static void main(String[] args) {

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(LocalDateTime.now().toString());
            }
        },3, 5, TimeUnit.SECONDS);


    }
}
