package com.rolex.master.manager;

import com.rolex.master.manager.loadbalance.RandomLoadBalance;
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
    private static Map<String, Channel> channels = new ConcurrentHashMap<>();

    public static Channel getChannel(String name) {
        return channels.get(name);
    }

    @Override
    public Map<String, Channel> getChannels() {
        return channels;
    }

    @Override
    public void addChannel(String name, Channel channel) {
        channels.put(name, channel);
    }

    @Override
    public void removeChannel(String name) {
        channels.remove(name);
    }


}
