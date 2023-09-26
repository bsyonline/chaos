package com.rolex.event.manager.exchange;

import com.rolex.model.Event;
import lombok.extern.slf4j.Slf4j;


/**
 * @author rolex
 * @since 2023/7/1
 */
@Slf4j
public abstract class DefaultRegistry implements EventRegistry {

    public abstract Event receive();

    public Event save(Event event) {
        log.info("save event");
        return event;
    }

    @Override
    public boolean registry() {
        Event event = receive();
        Event event1 = save(event);
        exchange(event1);
        return false;
    }

    private void exchange(Event event) {

    }
}
