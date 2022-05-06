package com.rolex.discovery.observer;

import com.rolex.discovery.routing.RoutingInfo;
import org.springframework.context.ApplicationEventPublisher;

import java.io.Serializable;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface Observer extends Serializable {
    void onChange(ApplicationEventPublisher applicationEventPublisher, RoutingInfo current, RoutingInfo changed);
}
