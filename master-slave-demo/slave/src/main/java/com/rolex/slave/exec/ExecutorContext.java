package com.rolex.slave.exec;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class ExecutorContext {

    static BlockingQueue synchronousQueue = new SynchronousQueue();
    static ExecutorService workerThreadPool = new ThreadPoolExecutor(5,5,0, TimeUnit.SECONDS, synchronousQueue);

    public static ExecutorService getWorkerThreadPool() {
        return workerThreadPool;
    }

    public static void setWorkerThreadPool(ExecutorService workerThreadPool) {
        ExecutorContext.workerThreadPool = workerThreadPool;
    }
}
