package com.rolex.slave;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@SpringBootApplication
@ComponentScan(value = "com.rolex")
@Slf4j
public class SlaveLauncher implements ApplicationListener<ContextRefreshedEvent> {
    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(SlaveLauncher.class).web(WebApplicationType.NONE).run(args);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("=================onApplicationEvent");
    }
}
