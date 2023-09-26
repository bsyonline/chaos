package com.rolex.event.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Component
@Slf4j
public class ModelServiceImplV1 {
    public void buildModelInstance(String request) {
        // 输入数据校验
        validateInput(request);
        // 根据输入创建模型实例
        String instance = createModelInstance(request);
        // 保存实例到相关 DB 表
        saveInstance(instance);
    }

    private void saveInstance(String instance) {
        log.info("--保存模型实例到相关DB表--");
    }

    private String createModelInstance(String request) {
        log.info("--根据输入数据创建模型实例--");
        return "test";
    }

    private void validateInput(String request) {
        log.info("--输入数据校验--");
    }
}
