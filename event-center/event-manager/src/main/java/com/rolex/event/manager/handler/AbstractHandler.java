package com.rolex.event.manager.handler;

import com.rolex.model.PipelineContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2023/7/11
 */
@Slf4j
public abstract class AbstractHandler implements EventHandler {
    @Override
    public boolean handle(PipelineContext context) {
        boolean b = doHandle(context);
        log.info("footprint={}", context.getFootprint());
        context.getFootprint().put(this.getClass().getSimpleName(), b);
        return b;
    }

    public abstract boolean doHandle(PipelineContext context);
}
