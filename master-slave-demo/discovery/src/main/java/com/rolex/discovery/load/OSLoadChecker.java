package com.rolex.discovery.load;

public interface OSLoadChecker {
    String CPU_LOAD = "cpu_load";
    String REMAINING_MEMORY_IN_MB = "remaining_memory_in_mb";
    String REMAINING_MEMORY_IN_PERCENT = "remaining_memory_in_percent";
    Long BYTE_TO_MB = 1024 * 1024L;
    Long KBYTE_TO_MB = 1024L;

    void fillCpuLoad(Props props);

    void fillMemoryLoad(Props props);
}