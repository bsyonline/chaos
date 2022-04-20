package test;


import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;

import java.util.Arrays;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class OSHITest {
    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        double[] systemLoadAverage = cpu.getSystemLoadAverage(1);
        System.out.println(Arrays.toString(systemLoadAverage));

        GlobalMemory memory = hal.getMemory();
        long total = memory.getTotal();
        System.out.println("total memory:" + FormatUtil.formatBytes(total));
        long available = memory.getAvailable();
        System.out.println("available memory:" + FormatUtil.formatBytes(available));
    }
}
