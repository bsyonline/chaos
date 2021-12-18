package com.rolex.alphax.concurrent.pool;

import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class DynamicThreadPoolExample {
    public static void main(String[] args) throws InterruptedException {
        dynamicModifyThreadPool();
    }

    private static void dynamicModifyThreadPool() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,
                5,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10),
                new ThreadPoolExecutor.AbortPolicy());
        IntStream.range(1, 16).forEach(i -> {
            executor.submit(() -> {
                try {
                    getThreadPoolStatus(executor, "init");
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        getThreadPoolStatus(executor, "preModify");
        executor.setCorePoolSize(10);
        executor.setMaximumPoolSize(10);
        while(true) {
            getThreadPoolStatus(executor, "postModify");
        }
    }

    public static void getThreadPoolStatus(ThreadPoolExecutor executor, String phase) throws InterruptedException {
        Thread.sleep(3000);
        System.out.printf("%s %s - %s - coreSize=%d, activeSize=%d, maxSize=%d, poolRate=%s, completed=%d, queueSize=%d, queued=%d, remain=%d, queueRate=%s%n",
                LocalDateTime.now(),
                Thread.currentThread().getName(),
                phase,
                executor.getCorePoolSize(),
                executor.getActiveCount(),
                executor.getMaximumPoolSize(),
                rate(executor.getActiveCount(),executor.getMaximumPoolSize()),
                executor.getCompletedTaskCount(),
                executor.getQueue().size() + executor.getQueue().remainingCapacity(),
                executor.getQueue().size(),
                executor.getQueue().remainingCapacity(),
                rate(executor.getQueue().size(),executor.getQueue().size() + executor.getQueue().remainingCapacity()));
    }

    public static String rate(int a, int b){
        return String.format("%1.2f%%", Double.parseDouble(a + "")/Double.parseDouble(b+"") * 100);
    }
}
