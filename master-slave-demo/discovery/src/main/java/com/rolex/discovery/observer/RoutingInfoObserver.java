package com.rolex.discovery.observer;

import com.rolex.discovery.event.MasterOfflineEvent;
import com.rolex.discovery.event.SlaveOfflineEvent;
import com.rolex.discovery.routing.NodeType;
import com.rolex.discovery.routing.RoutingInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

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
    public void onChange(ApplicationEventPublisher applicationEventPublisher, RoutingInfo current, RoutingInfo changed) {
        log.info("route info changed, current={}, changed={}", current, changed);
        if (changed.getType() == NodeType.client) {
            applicationEventPublisher.publishEvent(new SlaveOfflineEvent(this, current, changed));
        } else {
            applicationEventPublisher.publishEvent(new MasterOfflineEvent(this, current, changed));
        }
    }
}
