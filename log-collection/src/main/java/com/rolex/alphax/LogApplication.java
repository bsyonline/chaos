/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;

import com.rolex.alphax.util.MDCUtil;
import com.rolex.alphax.util.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootApplication
@RestController
@Slf4j
public class LogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }

    @GetMapping("/info")
    public String info() {
        MDCUtil.put();
        log.info("我是一条info日志");
        log.warn("我是一条warn日志");
        log.error("我是一条error日志");
        return "info";
    }

    @GetMapping("/err")
    public String error() {
        MDCUtil.put();
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            log.error("算术异常", e);
        }
        return "error";
    }

    @PostMapping("/watch")
    public void watch(@RequestBody String msg) {
        System.out.println("收到watch告警：" + msg);
    }

}
