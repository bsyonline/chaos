package com.rolex.master.manager;

import com.rolex.rpc.model.Manager;
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
public interface ExecutorManager extends Manager {

    @Override
    void addChannel(String name, Channel channel);

    @Override
    void removeChannel(String name);

    @Override
    Map<String, Channel> getChannels();

}
