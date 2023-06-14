package com.rolex.discovery.load;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor(staticName = "of")
public class ExecutorResourceMetrics implements ResourceMetrics {
    private static final String DISPLAY_TEMPLATE = "CPU 负载: %.2f, 阈值: %.2f; 剩余可用内存: %.2f (M), 阈值: %.2f (M)";

    private final double cpuLoad;
    private final double remainingMemInMB;
    private final double cpuLoadThreshold;
    private final double remainingMemInMBThreshold;

    @Override
    public boolean isCpuAndMemEnough() {
        return isCpuEnough() && isMemEnough();
    }

    private boolean isCpuEnough() {
        return cpuLoad <= cpuLoadThreshold;
    }

    private boolean isMemEnough() {
        return remainingMemInMB >= remainingMemInMBThreshold;
    }

    @Override
    public String toDisplay() {
        return String.format(DISPLAY_TEMPLATE, cpuLoad, cpuLoadThreshold, remainingMemInMB, remainingMemInMBThreshold);
    }
}
