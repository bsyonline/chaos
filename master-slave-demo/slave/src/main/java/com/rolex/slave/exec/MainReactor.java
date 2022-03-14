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
public class MainReactor extends Reactor {

    public static void accept(String msg) throws InterruptedException {
        log.info("task blocking queue size is {}", getTaskBlockingQueue().size());
        log.info("accept task:{}", msg);
        getTaskBlockingQueue().put(msg);
        log.info("task blocking queue size is {}", getTaskBlockingQueue().size());
    }

}
