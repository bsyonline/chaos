package com.rolex.event.manager.handler;

import com.google.gson.Gson;
import com.rolex.model.JobInstance;
import com.rolex.model.PipelineContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Component
@Slf4j
public class JobInstantiationHandler extends AbstractHandler {
    @Override
    public boolean doHandle(PipelineContext context) {
        log.info("JobInstantiationHandler: instant job - {}", new Gson().toJson(context));
        if(!context.isCompensate()) {
            int i = 1 / 0;
        }
        if(context.getFlowInstance().getId() != null) {
            JobInstance jobInstance = new JobInstance();
            jobInstance.setId(999L);
            jobInstance.setJobName("jobA");
            jobInstance.setStatus(10);
        }
        return true;
    }
}
