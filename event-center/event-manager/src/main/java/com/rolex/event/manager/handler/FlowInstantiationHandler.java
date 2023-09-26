package com.rolex.event.manager.handler;

import com.google.gson.Gson;
import com.rolex.model.FlowInstance;
import com.rolex.model.PipelineContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Component
@Slf4j
public class FlowInstantiationHandler extends AbstractHandler {

    @Override
    public boolean doHandle(PipelineContext context) {
        log.info("FlowInstantiationHandler: instant flow - {}", new Gson().toJson(context));
        FlowInstance flowInstance = new FlowInstance();
        flowInstance.setId(111L);
        flowInstance.setFlowName("aaa");
        flowInstance.setStatus(20);
        context.setFlowInstance(flowInstance);
        return true;
    }
}
