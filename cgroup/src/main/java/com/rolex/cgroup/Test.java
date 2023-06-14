package com.rolex.cgroup;

import java.io.IOException;
import java.util.UUID;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String traceId = UUID.randomUUID().toString();
        int cpu = 6;
        int mem = 50;
        long jobInstId = 1209;
        CgroupConfig cgroupConfig = new CgroupConfig();
        cgroupConfig.setCpuCoreUpperLimit(cpu);
        cgroupConfig.setMemoryUpperLimit(mem);
        CgroupManager cgroupManager = new CgroupManager(cgroupConfig);
        cgroupManager.createIfAbsentWorkerRootCgroup(traceId);
        String command = cgroupManager.startJobInstance(traceId, jobInstId);
        System.out.println(command);
    }
}
