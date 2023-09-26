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
public class CommonTailHandler implements ContextHandler<PipelineContext> {

    @Override
    public boolean handle(PipelineContext context) {
        // 设置处理结束时间
        context.setEndTime(LocalDateTime.now());


        log.info("管道执行完毕：context={}", new Gson().toJson(context));


        return true;
    }
}
