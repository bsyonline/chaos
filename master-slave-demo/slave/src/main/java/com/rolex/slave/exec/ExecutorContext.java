package com.rolex.slave.exec;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class ExecutorContext {

    static BlockingQueue taskBlockingQueue = new LinkedBlockingQueue<>();
    static ExecutorService workerThreadPool = Executors.newFixedThreadPool(5);

    public static BlockingQueue getTaskBlockingQueue() {
        return taskBlockingQueue;
    }

    public static void setTaskBlockingQueue(BlockingQueue taskBlockingQueue) {
        ExecutorContext.taskBlockingQueue = taskBlockingQueue;
    }

    public static ExecutorService getWorkerThreadPool() {
        return workerThreadPool;
    }

    public static void setWorkerThreadPool(ExecutorService workerThreadPool) {
        ExecutorContext.workerThreadPool = workerThreadPool;
    }
}
