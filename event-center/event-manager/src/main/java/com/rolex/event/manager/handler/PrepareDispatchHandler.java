package com.rolex.event.manager.handler;

import com.google.gson.Gson;
import com.rolex.model.PipelineContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Component
@Slf4j
public class PrepareDispatchHandler extends AbstractHandler {
    @Override
    public boolean doHandle(PipelineContext context) {
        log.info("PrepareDispatchHandler: prepare dispatch - {}", new Gson().toJson(context));
        return true;
    }
}
