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
    public static BlockingQueue<String> taskBufferQueue = new LinkedBlockingQueue<>(3);
}
