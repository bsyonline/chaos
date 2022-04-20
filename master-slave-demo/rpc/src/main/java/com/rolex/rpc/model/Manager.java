package com.rolex.rpc.model;

import io.netty.channel.Channel;

import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface Manager {
    void addChannel(String name, Channel channel);

    void removeChannel(String name);

    Map<String, Channel> getChannels();
}
