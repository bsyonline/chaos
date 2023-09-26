package com.rolex.event.demo;

import com.rolex.model.PipelineContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Component
@Slf4j
public class PipelineExecutor {

    @Resource
    private Map<Class<? extends PipelineContext>,
                List<? extends ContextHandler<? super PipelineContext>>> pipelineRouteMap;


    public boolean acceptSync(PipelineContext context) {
        Objects.requireNonNull(context, "上下文数据不能为 null");
        // 拿到数据类型
        Class<? extends PipelineContext> dataType = context.getClass();
        // 获取数据处理管道
        List<? extends ContextHandler<? super PipelineContext>> pipeline = pipelineRouteMap.get(dataType);


        if (CollectionUtils.isEmpty(pipeline)) {
            log.error("{} 的管道为空", dataType.getSimpleName());
            return false;
        }

        commonHeadHandler.handle(context);
        // 管道是否畅通
        boolean lastSuccess = true;


        for (ContextHandler<? super PipelineContext> handler : pipeline) {
            try {
                // 当前处理器处理数据，并返回是否继续向下处理
                lastSuccess = handler.handle(context);
            } catch (Throwable ex) {
                lastSuccess = false;
                log.error("[{}] 处理异常，handler={}", context.getEventName(), handler.getClass().getSimpleName(), ex);
            }


            // 不再向下处理
            if (!lastSuccess) { break; }
        }

        commonTailHandler.handle(context);
        return lastSuccess;
    }

    ExecutorService pipelineThreadPool = Executors.newFixedThreadPool(5);
    @Autowired
    private CommonHeadHandler commonHeadHandler;
    @Autowired
    private CommonTailHandler commonTailHandler;
    public void acceptAsync(PipelineContext context, BiConsumer<PipelineContext, Boolean> callback) {
        pipelineThreadPool.execute(() -> {
            boolean success = acceptSync(context);


            if (callback != null) {
                callback.accept(context, success);
            }
        });
    }
}
