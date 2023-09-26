package com.rolex.event.demo;

import com.google.gson.Gson;
import com.rolex.model.PipelineContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Component
@Slf4j
public class CommonHeadHandler implements ContextHandler<PipelineContext> {
    @Override
    public boolean handle(PipelineContext context) {
        log.info("管道开始执行：context={}", new Gson().toJson(context));

        // 设置开始时间
        context.setStartTime(LocalDateTime.now());

        return true;
    }
}
