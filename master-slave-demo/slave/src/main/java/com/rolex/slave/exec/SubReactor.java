package com.rolex.slave.exec;

import lombok.extern.slf4j.Slf4j;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class SubReactor extends Reactor {
    void dispatch() throws InterruptedException {
        String take = getTaskBlockingQueue().take();
        log.info("====SubReactor===={}", take);
        ExecutorContext.getWorkerThreadPool().submit(new WorkerThread(take));
    }
}
