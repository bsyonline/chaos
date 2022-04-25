package com.rolex.master.manager;

import com.rolex.discovery.routing.Host;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
public class DefaultExecutorManager implements ExecutorManager {
    private static Map<Host, Channel> channels = new ConcurrentHashMap<>();

    public static Channel getChannel(Host host) {
        return channels.get(host);
    }

    @Override
    public Map<Host, Channel> getChannels() {
        return channels;
    }

    @Override
    public void addChannel(Host host, Channel channel) {
        channels.put(host, channel);
    }

    @Override
    public void removeChannel(Host name) {
        channels.remove(name);
    }

}
