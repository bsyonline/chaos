package com.rolex.event.manager.receiver;

import com.rolex.event.manager.exchange.EventRegistry;

/**
 * @author rolex
 * @since 2023/7/1
 */
public class RocketMQReceiver implements EventRegistry {


    @Override
    public boolean registry() {
        return false;
    }
}
