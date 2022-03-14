package com.rolex.slave.exec;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class Reactor {
    static BlockingQueue<String> taskBlockingQueue = new LinkedBlockingQueue<>();

    public static BlockingQueue<String> getTaskBlockingQueue() {
        return taskBlockingQueue;
    }

    public static void setTaskBlockingQueue(BlockingQueue<String> taskBlockingQueue) {
        Reactor.taskBlockingQueue = taskBlockingQueue;
    }
}
