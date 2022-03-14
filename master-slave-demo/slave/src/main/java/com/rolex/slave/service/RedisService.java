package com.rolex.slave.service;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface RedisService {
    void sendChannelMessage(String channel, String message);
}
