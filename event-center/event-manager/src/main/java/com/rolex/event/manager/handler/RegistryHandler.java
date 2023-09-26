package com.rolex.event.manager.handler;

import com.google.gson.Gson;
import com.rolex.model.PipelineContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Slf4j
@Component
public class RegistryHandler extends AbstractHandler {
    @Override
    public boolean doHandle(PipelineContext context) {
        log.info("RegistryHandler: event registry - {}", new Gson().toJson(context));
        return true;
    }
}
