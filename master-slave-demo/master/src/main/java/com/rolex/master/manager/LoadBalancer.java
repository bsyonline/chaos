package com.rolex.master.manager;

import io.netty.channel.Channel;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface LoadBalancer {
    LoadBalanceStrategy select();
}
