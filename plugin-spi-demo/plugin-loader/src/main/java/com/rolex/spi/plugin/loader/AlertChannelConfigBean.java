package com.rolex.spi.plugin.loader;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "alerts")
public class AlertChannelConfigBean {
    List<AlertChannel> channels;
}
