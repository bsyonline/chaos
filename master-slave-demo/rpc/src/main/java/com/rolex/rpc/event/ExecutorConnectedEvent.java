package com.rolex.rpc.event;

import org.springframework.context.ApplicationEvent;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class ExecutorConnectedEvent extends ApplicationEvent {

    public ExecutorConnectedEvent(Object source) {
        super(source);
    }
}
