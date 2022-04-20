package com.rolex.slave.exec;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class SubReactorThread implements Runnable {

    BlockingQueue<String> queue;
    public SubReactorThread(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @SneakyThrows
    @Override
    public void run() {
        while(true) {
            String traceId = UUID.randomUUID().toString();
            log.info("{} take job before, queue size is {}", traceId, queue.size());
            String take = queue.take();
            log.info("{} take job after {}, queue size is {}", traceId, take, queue.size());
            ExecutorContext.getWorkerThreadPool().submit(new WorkerThread(take));
        }
    }
}
