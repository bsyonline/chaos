package com.rolex.event.manager.handler;

import com.rolex.model.PipelineContext;

/**
 * @author rolex
 * @since 2023/7/1
 */
public interface EventHandler {
    boolean handle(PipelineContext context);
}
