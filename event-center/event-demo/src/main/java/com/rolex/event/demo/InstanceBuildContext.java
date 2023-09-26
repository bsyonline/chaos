package com.rolex.event.demo;

import com.rolex.model.PipelineContext;
import lombok.Data;

import java.util.Map;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Data
public class InstanceBuildContext extends PipelineContext {
    private String modelId;
    private long userId;
    private Map<String, Object> formInput;
    private Long instanceId;
    private String errorMsg;

    @Override
    public String getEventName() {
        return "模型实例构建上下文";
    }

}
