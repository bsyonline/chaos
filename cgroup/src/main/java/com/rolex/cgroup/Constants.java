package com.rolex.cgroup;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class Constants {
    public static final String CGROUP_STATUS_FILE = "/proc/cgroups";

    public static final String MOUNT_STATUS_FILE = "/proc/mounts";

    public static final String DTS_CPU_CGROUP_NAME = "dtsexecutor";

    public static final String DTS_MEMORY_CGROUP_NAME = "dtsexecutor";
    /**
     * cpu层级树目录默认值
     */
    public static final String CPU_CGROUP_BASE_DIR = "/sys/fs/cgroup/cpu";
    /**
     * 内存层级树目录默认值
     */
    public static final String MEMORY_CGROUP_BASE_DIR = "/sys/fs/cgroup/memory";
    /**
     * 默认cpu使用上限
     */
    public static int CGROUP_CPU_UPLIMIT = 6;
    /**
     * 默认内存使用上限
     */
    public static int CGROUP_MEMORY_UPLIMIT = 50;
    /**
     * 单位转换 1G = 1 * 1024 * 1024 * 1024 bytes
     */
    public static long G_TO_BYTES = 1024 * 1024 * 1024;
    /**
     * cgroup控制组 cpu计量单位
     */
    public static long CPU_UNIT = 100000;
    /**
     * 不限制
     */
    public static int CGROUP_UN_LIMIT = -1;
    /**
     * 应用服务预留cpu 2核
     */
    public static int APP_SERVICE_RESERVED_CPU = 2;
    /**
     * 应用服务预留内存大小 6G
     */
    public static int APP_SERVICE_RESERVED_MEMORY = 6;
    /**
     * 系统核数控制阀值
     */
    public static int SYS_PROCESS_NUM_THRESHOLD = 4;
}
