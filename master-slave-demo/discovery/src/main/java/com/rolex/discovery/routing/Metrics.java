package com.rolex.discovery.routing;

import com.rolex.discovery.load.ExecutorResourceLoadChecker;
import com.rolex.discovery.load.ResourceLoadChecker;
import com.rolex.discovery.load.ResourceLoadContext;
import lombok.Data;

import java.io.Serializable;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Data
public class Metrics implements Serializable {
    private static final long serialVersionUID = -4498278210481188718L;
    public static final double CPU_LOAD_THRESHOLD = 0.8D / 100 * ExecutorResourceLoadChecker.FULL_CPU_LOAD;
    public static final double MEMORY_IN_MB_THRESHOLD = 1D * 1024;
    double cup;
    double memory;
    double cpuLoadThreshold;
    double memoryInMBThreshold;

    private Metrics(double cup, double memory) {
        this(cup, memory, CPU_LOAD_THRESHOLD, MEMORY_IN_MB_THRESHOLD);
    }

    public Metrics(double cup, double memory, double cpuLoadThreshold, double memoryInMBThreshold) {
        this.cup = cup;
        this.memory = memory;
        this.cpuLoadThreshold = cpuLoadThreshold;
        this.memoryInMBThreshold = memoryInMBThreshold;
    }

    private Metrics() {
    }

    public boolean isCpuAndMemEnough() {
        return isCpuEnough() && isMemEnough();
    }

    private boolean isCpuEnough() {
        return cup <= cpuLoadThreshold;
    }

    private boolean isMemEnough() {
        return memory >= memoryInMBThreshold;
    }

    private static ResourceLoadChecker localExecutorLoadChecker = ResourceLoadChecker.getInstance(ResourceLoadContext.getOsCheckerResourceLoadContext());

    public static Metrics of(double cpuLoadThreshold, double memoryInMBThreshold) {
        double[] resourceMetricsForNewJob = localExecutorLoadChecker.getResourceMetricsForNewJob();
        return new Metrics(resourceMetricsForNewJob[0], resourceMetricsForNewJob[1], cpuLoadThreshold, memoryInMBThreshold);
    }
}
