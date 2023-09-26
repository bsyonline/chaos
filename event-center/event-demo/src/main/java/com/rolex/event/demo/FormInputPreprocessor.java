package com.rolex.event.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Component
@Slf4j
public class FormInputPreprocessor implements ContextHandler<InstanceBuildContext> {
    @Override
    public boolean handle(InstanceBuildContext context) {
        log.info("--表单输入数据预处理--");


        // 假装进行表单输入数据预处理


        return true;
    }
}
