package com.rolex.spi.plugin.loader;

import com.rolex.spi.factory.SpiFactory;
import com.rolex.spi.plugin.SpiPlugin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

/**
 * @author rolex
 * @since 2023/9/25
 */
@SpringBootApplication
@Slf4j
public class Main implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        SpiFactory spiFactory = new SpiFactory(SpiPlugin.class);
        Map spiMap = spiFactory.getSpiMap();
        log.info("spiMap={}", spiMap);
    }
}
