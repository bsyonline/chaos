package com.rolex.discovery.load;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class DefaultOSLoadChecker implements OSLoadChecker {

    @Override
    public void fillCpuLoad(Props props) {
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        props.put(CPU_LOAD, osmxb.getSystemLoadAverage());
    }

    @Override
    public void fillMemoryLoad(Props props) {
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long freeMemory = osmxb.getFreePhysicalMemorySize() + osmxb.getFreeSwapSpaceSize();
        props.put(REMAINING_MEMORY_IN_MB, freeMemory / BYTE_TO_MB);
        props.put(REMAINING_MEMORY_IN_PERCENT, freeMemory / osmxb.getTotalPhysicalMemorySize());
    }
}
