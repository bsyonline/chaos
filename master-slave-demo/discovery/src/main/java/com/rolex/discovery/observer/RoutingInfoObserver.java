package com.rolex.discovery.observer;

import com.rolex.discovery.routing.RoutingInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class RoutingInfoObserver implements Observer {
    @Override
    public void onChange(RoutingInfo routingInfo) {
        log.info("route info changed, {}", routingInfo);
    }
}
