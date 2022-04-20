package com.rolex.slave.exec;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionException;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class MainReactor extends Reactor {

    public static boolean accept(String msg) throws InterruptedException {
//        log.info("task blocking queue size is {}", taskBufferQueue.size());
//        log.info("accept task:{}", msg);
//        boolean result = taskBufferQueue.offer(msg);
//        log.info("task blocking queue size is {}", taskBufferQueue.size());
//        return result;
        boolean result = false;
        try {
            ExecutorContext.getWorkerThreadPool().submit(new WorkerThread(msg));
            result = true;
        } catch (RejectedExecutionException e) {
            log.error("线程池满了，拒接新作业{}", msg);
            result = false;
        }
        return result;
    }

}
