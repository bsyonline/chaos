package com.rolex.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Data
public class PipelineContext {
    String traceId;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Event event;
    FlowInstance flowInstance;
    JobInstance jobInstance;
    boolean compensate;
    Map<String, Boolean> footprint = new HashMap<>();
}
