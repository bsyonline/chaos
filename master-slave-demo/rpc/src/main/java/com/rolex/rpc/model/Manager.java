package com.rolex.rpc.model;

import com.rolex.discovery.routing.Host;
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
    void addChannel(Host host, Channel channel);

    void removeChannel(Host host);

    Map<Host, Channel> getChannels();
}
