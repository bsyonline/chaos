package com.rolex.spi;

import lombok.extern.slf4j.Slf4j;

import java.util.ServiceLoader;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
public class SpiLoader {
    public static void main(String[] args) {
        ServiceLoader<HelloSpi> serviceLoader = ServiceLoader.load(HelloSpi.class);
        for (HelloSpi helloSpi : serviceLoader) {
            log.info("-----------------------------------------");
            log.info("classLoader = {}", helloSpi.getClass().getClassLoader());
            helloSpi.sayHello();
        }
    }
}
