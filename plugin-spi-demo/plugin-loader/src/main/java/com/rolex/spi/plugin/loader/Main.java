package com.rolex.spi.plugin.loader;

import com.rolex.spi.AlertPlugin;
import com.rolex.spi.factory.SpiFactory;
import com.rolex.spi.plugin.SpiPlugin;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * @author rolex
 * @since 2023/9/25
 */
@SpringBootApplication
@Slf4j
@ConfigurationProperties(prefix = "alert")
@Data
public class Main implements CommandLineRunner {

    String[] channels;

    @Autowired
    AlertChannelConfigBean alertChannelConfigBean;

    @Autowired
    AlarmConfigBean alarmConfigBean;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        SpiFactory spiFactory = new SpiFactory(SpiPlugin.class);
        Map<String, AlertPlugin> spiMap = spiFactory.getSpiMap();
        log.info("spiMap={}", spiMap);
        log.info("channels={}", channels);
        log.info("channels.len={}", channels.length);
        log.info("alertChannels={}", alertChannelConfigBean.getChannels());
        log.info("alarms={}", alarmConfigBean.getChannels());

        if (alarmConfigBean.getChannels() != null && !alarmConfigBean.getChannels().isEmpty()) {
            for (Map.Entry<String, Alarm> kv : alarmConfigBean.getChannels().entrySet()) {
                spiMap.get(kv.getKey().toLowerCase()).alert();
            }
        }
    }
}
