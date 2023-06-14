package com.rolex.discovery.load;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class GroupResourceMetrics implements ResourceMetrics {
    private static final String DISPLAY_TEMPLATE = "执行资源需求：%.2f，可用执行资源：CPU: %.2f，执行内存需求： %.2f (M)，可用内存量：%.2f (M)，正在等待资源释放";

    private final int requiredCU;
    private final double allowedCPUs;
    private final double allowedMemInMB;

    @Override
    public boolean isCpuAndMemEnough() {
        return allowedCPUs >= (float) requiredCU && allowedMemInMB >= (float) requiredCU * 4 * 1024.00;
    }

    @Override
    public String toDisplay() {
        return String.format(DISPLAY_TEMPLATE, (float) requiredCU, allowedCPUs, requiredCU * 4 * 1024.00, allowedMemInMB);
    }
}