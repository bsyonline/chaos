package com.rolex.master.listener;

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
    public void resetDispatchingJobStatus(MasterOfflineEvent event) throws Exception {
        if (event.getCurrent().getType() == NodeType.server && event.getChanged().getType() == NodeType.server) {
            log.info("====MasterOfflineEvent=======重置派发机器上的作业状态======current={}, changed={}", event.getCurrent(), event.getChanged());
        }
    }

    @EventListener
    public void processRedispatch(SlaveOfflineEvent event) throws Exception {
        if (event.getCurrent().getType() == NodeType.server && event.getChanged().getType() == NodeType.client) {
            log.info("====SlaveOfflineEvent=======处理重派======current={}, changed={}", event.getCurrent(), event.getChanged());
        }
    }
}
