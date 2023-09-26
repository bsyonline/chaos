package com.rolex.event.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Component
@Slf4j
public class ModelServiceImplV2 {
    @Autowired
    PipelineExecutor pipelineExecutor;

    public void buildModelInstance(String request) {
        InstanceBuildContext data = createPipelineData(request);
        boolean success = pipelineExecutor.acceptSync(data);
    }

    private InstanceBuildContext createPipelineData(String request) {
        InstanceBuildContext instanceBuildContext = new InstanceBuildContext();
        instanceBuildContext.setModelId("modelId");
        instanceBuildContext.setUserId(1);
        instanceBuildContext.setInstanceId(1L);
        instanceBuildContext.setEventName("testEvent");
        return instanceBuildContext;
    }
}
