package com.rolex.event.demo;

import com.rolex.model.PipelineContext;

/**
 * @author rolex
 * @since 2023/7/1
 */
public interface  ContextHandler<T extends PipelineContext> {
    boolean handle(T context);
}
