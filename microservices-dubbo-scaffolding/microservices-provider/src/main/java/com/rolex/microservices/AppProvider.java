package com.rolex.microservices;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@EnableDubbo
@SpringBootApplication
public class AppProvider {
    public static void main(String[] args) {
        SpringApplication.run(AppProvider.class, args);
    }
}
