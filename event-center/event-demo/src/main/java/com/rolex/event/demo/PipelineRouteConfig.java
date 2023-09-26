package com.rolex.event.demo;


import com.rolex.model.PipelineContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Configuration
public class PipelineRouteConfig implements ApplicationContextAware {

    private static final
    Map<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>> PIPELINE_ROUTE_MAP = new HashMap<>(4);

    static {
        PIPELINE_ROUTE_MAP.put(InstanceBuildContext.class,
                Arrays.asList(
                        InputDataPreChecker.class,
                        FormInputPreprocessor.class,
                        BizSideCustomProcessor.class,
                        ModelInstanceCreator.class,
                        ModelInstanceSaver.class
                ));


        // 将来其他 Context 的管道配置
    }

    @Bean("pipelineRouteMap")
    public Map<Class<? extends PipelineContext>, List<? extends ContextHandler<? extends PipelineContext>>> getHandlerPipelineMap() {
        return PIPELINE_ROUTE_MAP.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, this::toPipeline));
    }

    private List<? extends ContextHandler<? extends PipelineContext>> toPipeline(
            Map.Entry<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>> entry) {
        return entry.getValue()
                .stream()
                .map(appContext::getBean)
                .collect(Collectors.toList());
    }

    private ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }
}
