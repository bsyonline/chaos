package com.rolex.event.demo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Component
@Slf4j
public class ModelInstanceCreator implements ContextHandler<InstanceBuildContext> {
    @Override
    public boolean handle(InstanceBuildContext context) {
        log.info("--根据输入数据创建模型实例--");


        // 假装创建模型实例


        return true;
    }
}
