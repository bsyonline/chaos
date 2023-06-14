package com.rolex.rpc.util;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class Constants {

    public static final int READER_IDLE_TIME_SECONDS = 20;

    public static final int WRITER_IDLE_TIME_SECONDS = 20;

    public static final int ALL_IDLE_TIME_SECONDS = READER_IDLE_TIME_SECONDS * 3 + 1;

    public static final String OS_NAME = System.getProperty("os.name");

    public static final String NETTY_EPOLL_ENABLE = System.getProperty("netty.epoll.enable", "true");

}
