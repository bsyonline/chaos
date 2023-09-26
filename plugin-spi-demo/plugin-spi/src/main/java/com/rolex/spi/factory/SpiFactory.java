package com.rolex.spi.factory;

import com.rolex.spi.plugin.SpiPlugin;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author rolex
 * @since 2023/9/25
 */
@Getter
@Slf4j
public class SpiFactory<T extends SpiPlugin> {
    private final Map<String, T> spiMap = new ConcurrentHashMap<>();
    public SpiFactory(Class<T> spiClass){
        ServiceLoader<T> load = ServiceLoader.load(spiClass);
        for(T t : load){
            spiMap.put(t.name(), t);
            log.info("plugin [{}] loaded", t.name());
        }
    }

}
