package com.rolex.spi.plugin.loader;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "alarm")
public class AlarmConfigBean {
    Map<String, Alarm> channels;
}
