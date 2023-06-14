package com.rolex.slave.listener;

import com.rolex.discovery.event.MasterOfflineEvent;
import com.rolex.discovery.event.SlaveOfflineEvent;
import com.rolex.discovery.routing.NodeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
@Slf4j
public class OfflineEventListener {
    @EventListener
    public void offline(SlaveOfflineEvent event) throws Exception {
        if (event.getCurrent().getType() == NodeType.client && event.getChanged().getType() == NodeType.client) {
            log.info("====SlaveOfflineEvent=======处理执行机下线======current={}, changed={}", event.getCurrent(), event.getChanged());
        }
    }
}
