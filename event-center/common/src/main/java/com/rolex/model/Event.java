package com.rolex.model;

import lombok.Data;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Data
public class Event {
    private String eventName;
    private String eventId;
    private EventType eventType;
    private int status;
}
