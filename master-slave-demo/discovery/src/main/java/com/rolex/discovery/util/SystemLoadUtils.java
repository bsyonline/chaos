package com.rolex.discovery.util;

import com.rolex.discovery.load.ExecutorResourceLoadChecker;
import com.rolex.discovery.load.Props;
import com.rolex.discovery.load.ResourceLoadChecker;
import com.rolex.discovery.load.ResourceLoadContext;
import com.rolex.discovery.load.ResourceMetrics;
import com.rolex.discovery.routing.Metrics;

import java.util.concurrent.Executor;

import static com.rolex.discovery.util.Constants.CPU_THRESHOLD;
import static com.rolex.discovery.util.Constants.MEM_THRESHOLD;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class SystemLoadUtils {
    private static ResourceLoadChecker localExecutorLoadChecker = ResourceLoadChecker.getInstance(ResourceLoadContext.getOsCheckerResourceLoadContext());

    private static double[] hasEnoughLocalExecutorResourceForNewJob() {
        // cpu 负载阈值，百分比转换为当前机器的实际满载
        Double cpuThreshold = 0.8D / 100 * ExecutorResourceLoadChecker.FULL_CPU_LOAD;
        // mem 剩余阈值，G 转 M
        Double memThreshold = 1D * 1024;

        Props props = new Props();
        props.put(CPU_THRESHOLD, cpuThreshold);
        props.put(MEM_THRESHOLD, memThreshold);

        return localExecutorLoadChecker.getResourceMetricsForNewJob();
    }

    public static void main(String[] args) {
        System.out.println(hasEnoughLocalExecutorResourceForNewJob());
    }
}
