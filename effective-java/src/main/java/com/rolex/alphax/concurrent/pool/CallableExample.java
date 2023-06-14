package com.rolex.alphax.concurrent.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2023
 */
@Slf4j
public class CallableExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("dispatchThread" + "-%d")
                .setDaemon(true).build();
        ExecutorService threadPool = new ThreadPoolExecutor(10,
                10,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),
                threadFactory);
        CountDownLatch latch = new CountDownLatch(5);
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Future<Integer> future = threadPool.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    log.info("thread"+ finalI +"开始");
                    Thread.sleep(5000);
                    latch.countDown();
                    log.info("thread"+ finalI +"结束");
                    return finalI;
                }
            });
            futures.add(future);
        }
        latch.await();
        int count = 0 ;
        for (Future<Integer> future : futures) {
            count += future.get();
        }
        System.out.println(count);
        threadPool.shutdown();
    }

    class CustomizeThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            return null;
        }
    }
}
