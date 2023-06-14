package com.rolex.cgroup;

import lombok.Data;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Data
public class CgroupConfig {
    private static final long serialVersionUID = -4090661081774136142L;

    /**
     * 层级树目录
     */
    private String cpuCgroupBaseDir;
    /**
     * 层级树目录
     */
    private String memoryCgroupBaseDir;
    /**
     * cpu限制核数
     */
    private int cpuCoreUpperLimit = -1;
    /**
     * 内存限制大小 单位为G
     */
    private long memoryUpperLimit = -1;
}
