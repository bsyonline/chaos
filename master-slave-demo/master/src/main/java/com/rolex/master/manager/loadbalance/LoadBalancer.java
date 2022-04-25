package com.rolex.master.manager.loadbalance;

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
    Channel select();
}
