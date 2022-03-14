package com.rolex.slave.exec;

import lombok.SneakyThrows;
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
public class SubReactorThread implements Runnable {

    SubReactor reactor;

    public SubReactorThread(SubReactor reactor) {
        this.reactor = reactor;
    }

    @SneakyThrows
    @Override
    public void run() {
        while(true) {
            log.info("=========sub reactor");
            reactor.dispatch();
        }
    }
}
