package com.rolex.event.manager.pipeline;

import com.google.gson.Gson;
import com.rolex.event.manager.handler.EventHandler;
import com.rolex.model.PipelineContext;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Slf4j
public class EventPipeline implements Pipeline {

    List<EventHandler> pipeline;

    public Pipeline pipeline() {
        pipeline = Lists.newArrayList();
        return this;
    }

    @Override
    public Pipeline addLast(EventHandler handler) {
        pipeline.add(handler);
        return this;
    }

    // 管道是否畅通
    boolean lastSuccess = true;

    @Override
    public void exec(PipelineContext context) {
        for (EventHandler handler : pipeline) {
            Boolean aBoolean = context.getFootprint().get(handler.getClass().getSimpleName());
            if (aBoolean != null && aBoolean) {
                log.info("{} skipped", this.getClass().getSimpleName());
                continue;
            }
            try {
                // 当前处理器处理数据，并返回是否继续向下处理
                lastSuccess = handler.handle(context);
            } catch (Throwable ex) {
                lastSuccess = false;
                log.error("[{}] 处理异常，handler={}, context={}", context.getEvent().getEventName(), handler.getClass().getSimpleName(), new Gson().toJson(context), ex);
            }
            // 不再向下处理
            if (!lastSuccess) {
                break;
            }
        }
    }
}
