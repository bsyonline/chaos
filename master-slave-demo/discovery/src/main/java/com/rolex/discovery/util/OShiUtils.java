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

    public static double getCpuLoad(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        double[] systemLoadAverage = cpu.getSystemLoadAverage(1);
        return systemLoadAverage[0];
    }

    public static double getMemoryLoad(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        GlobalMemory memory = hal.getMemory();
        long total = memory.getTotal();
        System.out.println("total memory:" + FormatUtil.formatBytes(total));
        long available = memory.getAvailable();
        System.out.println("available memory:" + FormatUtil.formatBytes(available));
        return 0;
    }
}
