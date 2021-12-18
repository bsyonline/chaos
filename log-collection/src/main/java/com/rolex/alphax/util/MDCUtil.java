/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.util;

import org.slf4j.MDC;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2020
 */
@Component
public class MDCUtil implements EnvironmentAware {
    private static Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        MDCUtil.environment = environment;
    }

    public static void put() {
        MDC.put("host", NetUtil.getLocalHost());
        MDC.put("ip", NetUtil.getLocalIp());
        MDC.put("appName", environment.getProperty("spring.application.name"));
    }
}
