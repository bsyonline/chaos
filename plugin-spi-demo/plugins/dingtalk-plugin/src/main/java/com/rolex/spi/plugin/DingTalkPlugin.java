package com.rolex.spi.plugin;

import com.google.auto.service.AutoService;
import com.rolex.spi.AlertPlugin;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2023/9/25
 */
@AutoService(SpiPlugin.class)
@Slf4j
public class DingTalkPlugin implements AlertPlugin {
    @Override
    public String name() {
        return "dingTalk";
    }


    @Override
    public void alert() {
        log.info("dingTalk alert");
    }
}
