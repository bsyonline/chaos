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
public class WorkerThread extends Thread{

    String msg;

    public WorkerThread(String msg) {
        this.msg = msg;
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info("开始执行：{}", msg);
        Thread.sleep(15000);
        log.info("执行结束：{}", msg);
    }
}
