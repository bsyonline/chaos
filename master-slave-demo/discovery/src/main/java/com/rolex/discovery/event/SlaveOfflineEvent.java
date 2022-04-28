package com.rolex.discovery.event;

import com.rolex.discovery.routing.RoutingInfo;
import org.springframework.context.ApplicationEvent;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class SlaveOfflineEvent extends ApplicationEvent {

    RoutingInfo current;
    RoutingInfo changed;

    public SlaveOfflineEvent(Object source, RoutingInfo current, RoutingInfo changed) {
        super(source);
        this.current = current;
        this.changed = changed;
    }

    public RoutingInfo getCurrent() {
        return current;
    }

    public void setCurrent(RoutingInfo current) {
        this.current = current;
    }

    public RoutingInfo getChanged() {
        return changed;
    }

    public void setChanged(RoutingInfo changed) {
        this.changed = changed;
    }
}
