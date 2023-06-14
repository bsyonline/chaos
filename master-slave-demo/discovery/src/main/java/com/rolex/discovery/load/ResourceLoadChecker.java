package com.rolex.discovery.load;

import com.rolex.discovery.routing.Metrics;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class ResourceLoadChecker {
    private static final Map<ResourceType, ResourceLoadChecker> CHECKER_CACHE = new ConcurrentHashMap<>();

    protected boolean cache;
    protected long lastCheckTime = 0;
    protected long cacheTimeInMillSec;

    public static ResourceLoadChecker getInstance(ResourceLoadContext context) {
        return getInstance(context, false);
    }

    private static ResourceLoadChecker getInstance(ResourceLoadContext context, boolean refreshCache) {
        ResourceLoadChecker loadChecker = CHECKER_CACHE.get(context.getResourceType());

        if (loadChecker == null || refreshCache) {
            switch (context.getResourceType()) {
                case Executor:
                    log.info("获取ExecutorResourceLoadChecker实例");
                    loadChecker = new ExecutorResourceLoadChecker(context);
                    break;
                case K8S:
                case Yarn:
                case MPP:
                default:
                    log.info("获取ResourceGroupLoadChecker实例");
            }
            CHECKER_CACHE.put(context.getResourceType(), loadChecker);
        }
        return loadChecker;
    }

    /**
     * 检测系统负载
     */
    protected abstract Props getResourceLoad(Props props);

    /**
     * 检测资源是否满足需求
     */
    public abstract double[] getResourceMetricsForNewJob();

    /**
     * 检测资源是否满足需求
     */
    public abstract ResourceMetrics getResourceMetricsForNewJob(String traceId, ResourceSnapshotService snapshotService, Props props);
}