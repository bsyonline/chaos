package com.rolex.discovery.load;

import com.rolex.discovery.routing.Metrics;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import static com.rolex.discovery.util.Constants.CPU_THRESHOLD;
import static com.rolex.discovery.util.Constants.MEM_THRESHOLD;

@Slf4j
public class ExecutorResourceLoadChecker extends ResourceLoadChecker {
    private Map<EPlatform, OSLoadChecker> osLoadCheckers = new ConcurrentHashMap<>();
    // CPU 负载，满载定义为当前CPU核数，即 1 表示单核满载，多核时 1 乘以相应核数表示满载
    public static final int FULL_CPU_LOAD = Runtime.getRuntime().availableProcessors();

    public ExecutorResourceLoadChecker(ResourceLoadContext context) {
        this.cacheTimeInMillSec = context.getCacheTimeInMillSec();
    }

    @Override
    protected synchronized Props getResourceLoad(Props props) {
        EPlatform platform = EPlatform.ofLocalPlatform();

        OSLoadChecker checker = getOSLoadChecker(platform);
        Props result = new Props();
        if (!cache || System.currentTimeMillis() - lastCheckTime > cacheTimeInMillSec) {
            checker.fillCpuLoad(result);
            checker.fillMemoryLoad(result);
            lastCheckTime = System.currentTimeMillis();
        }

        return result;
    }


    @Override
    public double[] getResourceMetricsForNewJob() {
        Props props = new Props();
        Props resourceLoad = getResourceLoad(props);
        double cpuLoad = resourceLoad.getDouble(OSLoadChecker.CPU_LOAD, 0D);
        double remainingMemoryInMB = resourceLoad.getDouble(OSLoadChecker.REMAINING_MEMORY_IN_MB, Integer.MAX_VALUE);

        return new double[]{cpuLoad, remainingMemoryInMB};

    }

    @Override
    @Deprecated
    public ResourceMetrics getResourceMetricsForNewJob(String traceId, ResourceSnapshotService snapshotService, Props props) {
        //执行机不需要资源快照，本方法不会被调用到
        return null;
    }

    private OSLoadChecker getOSLoadChecker(EPlatform platform) {
        OSLoadChecker checker = osLoadCheckers.get(platform);
        if (checker != null) {
            return checker;
        }

        switch (platform) {
            case Linux:
                checker = new LinuxLoadChecker();
                break;
            default:
                checker = new DefaultOSLoadChecker();
        }
        osLoadCheckers.put(platform, checker);
        return checker;
    }

    public enum EPlatform {
        Linux("Linux"),
        Windows10("Windows 10"),
        Windows("Windows");

        @Getter
        private String name;

        EPlatform(String name) {
            this.name = name;
        }

        public static EPlatform ofLocalPlatform() {
            String os = System.getProperty("os.name");
            EPlatform osEPlatform = EPlatform.of(os);
            return osEPlatform != null ? osEPlatform : Linux;
        }

        private static EPlatform of(String name) {
            EPlatform[] platforms = EPlatform.values();
            for (EPlatform platform : platforms) {
                if (platform.getName().equals(name)) {
                    return platform;
                }
            }
            return null;
        }
    }
}
