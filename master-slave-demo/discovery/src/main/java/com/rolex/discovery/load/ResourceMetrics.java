package com.rolex.discovery.load;

public interface ResourceMetrics {
    boolean isCpuAndMemEnough();

    String toDisplay();
}