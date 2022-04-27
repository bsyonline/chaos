package com.rolex.discovery.util;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;


/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class OShiUtils {
    private static final long KIBI = 1L << 10;
    private static final long MEBI = 1L << 20;
    private static final long GIBI = 1L << 30;
    private static final long TEBI = 1L << 40;
    private static final long PEBI = 1L << 50;
    private static final long EXBI = 1L << 60;

    public static double getCpuLoad() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        double[] systemLoadAverage = cpu.getSystemLoadAverage(1);
        return systemLoadAverage[0];
    }

    public static double getMemoryLoad() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        GlobalMemory memory = hal.getMemory();
        long available = memory.getAvailable();
        return format(available);
    }

    private static double format(long bytes) {
        return bytes / 1024 / 1024 / 1024;
    }

}
