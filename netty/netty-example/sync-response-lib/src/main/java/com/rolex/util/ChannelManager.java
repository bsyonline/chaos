package com.rolex.util;

import io.netty.channel.Channel;

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
public class ChannelManager {

    public static final Map<String, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();

}
