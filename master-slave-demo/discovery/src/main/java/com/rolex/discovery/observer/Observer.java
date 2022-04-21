package com.rolex.discovery.observer;

import com.rolex.discovery.routing.RoutingInfo;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface Observer {
    void onChange(RoutingInfo routingInfo);
}
