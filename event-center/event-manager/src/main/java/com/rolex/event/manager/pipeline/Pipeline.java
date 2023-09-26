package com.rolex.event.manager.pipeline;

import com.rolex.event.manager.handler.EventHandler;
import com.rolex.model.PipelineContext;

/**
 * @author rolex
 * @since 2023/7/1
 */
public interface Pipeline {

    void exec(PipelineContext context);

    Pipeline addLast(EventHandler handler);

}
