package com.rolex.event.manager.exchange;

import com.rolex.event.manager.handler.DependencyCheckHandler;
import com.rolex.event.manager.handler.FlowInstantiationHandler;
import com.rolex.event.manager.handler.JobInstantiationHandler;
import com.rolex.event.manager.handler.PrepareDispatchHandler;
import com.rolex.event.manager.handler.RegistryHandler;
import com.rolex.event.manager.pipeline.InnerEventPipeline;
import com.rolex.model.PipelineContext;

/**
 * @author rolex
 * @since 2023/7/1
 */
public class EventExchange {
    public void exchange(PipelineContext context) {
        switch (context.getEvent().getEventType()) {
            case innerEvent:
                new InnerEventPipeline()
                        .pipeline()
                        .addLast(new RegistryHandler())
                        .addLast(new DependencyCheckHandler())
                        .addLast(new PrepareDispatchHandler())
                        .exec(context);
                break;
            case outerFlowEvent:
                new InnerEventPipeline()
                        .pipeline()
                        .addLast(new RegistryHandler())
                        .addLast(new FlowInstantiationHandler())
                        .addLast(new JobInstantiationHandler())
                        .addLast(new PrepareDispatchHandler())
                        .exec(context);
                break;
            case outerJobEvent:
                break;
            case startEvent:
                break;
            default:
        }
    }
}
