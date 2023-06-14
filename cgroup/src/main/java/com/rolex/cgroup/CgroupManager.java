package com.rolex.cgroup;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
public class CgroupManager implements Manager {

    /**
     * 层级树
     */
    private Hierarchy cpuHierarchy;
    /**
     * 层级树
     */
    private Hierarchy memoryHierarchy;
    /**
     * cpu cgroup控制组
     */
    private CgroupCommon cpuRootCgroup;
    /**
     * memory cgroup控制组
     */
    private CgroupCommon memoryRootCgroup;
    /**
     * cpu层级树目录默认值
     */
    private String cpuCgroupBaseDir;
    /**
     * 内存层级树目录默认值
     */
    private String memoryCgroupBaseDir;
    /**
     * 配置
     */
    CgroupConfig config;

    public CgroupManager(CgroupConfig config) {
        log.info("running on cgroup mode");
        this.config = config;
        this.cpuCgroupBaseDir = config.getCpuCgroupBaseDir() == null ? Constants.CPU_CGROUP_BASE_DIR : config.getCpuCgroupBaseDir();
        this.memoryCgroupBaseDir = config.getMemoryCgroupBaseDir() == null ? Constants.MEMORY_CGROUP_BASE_DIR : config.getMemoryCgroupBaseDir();
        if (enable()) {
            assemble();
        }
    }

    @Override
    public boolean enable() {
        return CgroupUtils.enabled();
    }

    @Override
    public void reloadCgroupConfig(String traceId) throws IOException {
        CgroupCommon workerCpuGroup = assembleCpuWorkerGroup();
        CgroupCommon workerMemoryGroup = assembleMemoryWorkerGroup();
        CgroupCore cpu = workerCpuGroup.getCores().get(SubSystemType.cpu);
        CpuCore cpuCore = (CpuCore) cpu;
        if (cpuCore.getCpuCfsQuotaUs().intValue() != config.getCpuCoreUpperLimit() * Constants.CPU_UNIT) {
            log.info("{} cpu cgroup file value {} diffrent with global config getCpuCoreUpperLimit {} ", traceId, cpuCore.getCpuCfsQuotaUs().intValue(), config.getCpuCoreUpperLimit() * Constants.CPU_UNIT);
            setCpuUsageUpperLimit(traceId, cpuCore);
        }
        CgroupCore memory = workerMemoryGroup.getCores().get(SubSystemType.memory);
        MemoryCore memoryCore = (MemoryCore) memory;
        if (memoryCore.getPhysicalUsageLimit() != config.getMemoryUpperLimit() * Constants.G_TO_BYTES) {
            log.info("{} mem cgroup file value {} diffrent with global config getMemoryUpperLimit {} ", traceId, memoryCore.getPhysicalUsageLimit(), config.getMemoryUpperLimit() * Constants.G_TO_BYTES);
            setMemoryUsageUpperLimit(traceId, memoryCore);
        }
    }

    @Override
    public void createIfAbsentWorkerRootCgroup(String traceId) throws IOException {
        log.debug("{} createIfAbsentWorkerRootCgroup", traceId);
        if (enable()) {
            if (!CgroupUtils.dirExists(cpuCgroupBaseDir + "/" + Constants.DTS_CPU_CGROUP_NAME)) {
                log.debug("{} cpu cgroup dir not exists, next create it", traceId);
                CgroupCommon workerCpuGroup = new CgroupCommon(Constants.DTS_CPU_CGROUP_NAME, this.cpuHierarchy, this.cpuRootCgroup);
                CgroupOperationCenter.getInstance().create(traceId, workerCpuGroup);
                CgroupCore cpu = workerCpuGroup.getCores().get(SubSystemType.cpu);
                CpuCore cpuCore = (CpuCore) cpu;
                setCpuUsageUpperLimit(traceId, cpuCore);
            }
            if (!CgroupUtils.dirExists(memoryCgroupBaseDir + "/" + Constants.DTS_MEMORY_CGROUP_NAME)) {
                log.debug("{} memory cgroup dir not exists, next create it", traceId);
                CgroupCommon workerMemoryGroup = new CgroupCommon(Constants.DTS_MEMORY_CGROUP_NAME, this.memoryHierarchy, this.memoryRootCgroup);
                CgroupOperationCenter.getInstance().create(traceId, workerMemoryGroup);
                CgroupCore memory = workerMemoryGroup.getCores().get(SubSystemType.memory);
                MemoryCore memoryCore = (MemoryCore) memory;
                setMemoryUsageUpperLimit(traceId, memoryCore);
            }
        }
    }

    /**
     * 启动作业实例
     * 此方法目前没有做任何实质上的事情，如果要控制到作业层级的话，此方法扩展
     *
     * @param jobInstId
     * @return
     */
    @Override
    public String startJobInstance(String traceId, Long jobInstId) {
        if (enable()) {
            CgroupCommon workerCpuGroup = assembleCpuWorkerGroup();
            CgroupCommon workerMemoryGroup = assembleMemoryWorkerGroup();
            StringBuilder sb = new StringBuilder();
            sb.append("cgexec -g cpu:").append(workerCpuGroup.getName()).append(" ");
            sb.append("-g memory:").append(workerMemoryGroup.getName()).append(" ");
            log.info("{} STARTING A PROCESS IN A CONTROL GROUP 「{}」, jobInstId {}", traceId, sb.toString(), jobInstId);
            return sb.toString();
        }
        return null;
    }

    private void assemble() {
        try {
            String traceId = UUID.randomUUID().toString();
            cpuHierarchy = assembleCpuHierarchy(traceId);
            memoryHierarchy = assembleMemoryHierarchy(traceId);
            cpuRootCgroup = assembleCpuCgroupCommon();
            memoryRootCgroup = assembleMemoryCgroupCommon();
        } catch (Exception e) {
            log.error("assemble throw error", e);
        }
    }

    /**
     * 组装层级树
     *
     * @return
     */
    private Hierarchy assembleCpuHierarchy(String traceId) throws IOException {
        Hierarchy h = CgroupOperationCenter.getInstance().busy(traceId, SubSystemType.cpu);
        if (h == null) {
            Set<SubSystemType> types = new HashSet<>();
            types.add(SubSystemType.cpu);
            h = new Hierarchy(Constants.DTS_CPU_CGROUP_NAME, types, cpuCgroupBaseDir);
            CgroupOperationCenter.getInstance().mount(traceId, h);
        }
        return h;
    }

    private Hierarchy assembleMemoryHierarchy(String traceId) throws IOException {
        Hierarchy h = CgroupOperationCenter.getInstance().busy(traceId, SubSystemType.memory);
        if (h == null) {
            Set<SubSystemType> types = new HashSet<>();
            types.add(SubSystemType.memory);
            h = new Hierarchy(Constants.DTS_MEMORY_CGROUP_NAME, types, memoryCgroupBaseDir);
            CgroupOperationCenter.getInstance().mount(traceId, h);
        }
        return h;
    }

    private CgroupCommon assembleCpuCgroupCommon() throws IOException {
        return cpuHierarchy.getRootCgroups();
    }

    private CgroupCommon assembleMemoryCgroupCommon() throws IOException {
        return memoryHierarchy.getRootCgroups();
    }

    /**
     * 设置cpu占用核数上限
     *
     * @param cpuCore
     * @throws IOException
     */
    private void setCpuUsageUpperLimit(String traceId, CpuCore cpuCore) throws IOException {
        /*
         * User cfs_period & cfs_quota to control the upper limit use of cpu core e.g.
         * If making a process to fully use two cpu cores, set cfs_period_us to
         * 100000 and set cfs_quota_us to 200000 The highest value of "cpu core upper limit" is cpuCoreUpperLimit
         */
        long cpuCoreUpperLimit = validateCpuUpperLimitValue();
        log.debug("{} validateCpuUpperLimitValue return {}", traceId, cpuCoreUpperLimit);
        if (cpuCoreUpperLimit == -1) {
            // No control of cpu usage
            cpuCore.setCpuCfsQuotaUs(traceId, cpuCoreUpperLimit);
        } else {
            cpuCore.setCpuCfsPeriodUs(Constants.CPU_UNIT);
            cpuCore.setCpuCfsQuotaUs(traceId, cpuCoreUpperLimit);
        }
    }

    /**
     * 系统总内存 刨除6G用于应用服务。剩下的可作为作业计算使用
     * 如果配置的阀值大于可分配内存，则设置为不限制
     * 如果配置的阀值小于等于可分配内存，则设置为配置阀值
     *
     * @return
     */
    private long validateMemoryUpperLimitValue(String traceId) {
        long totalMem = LinuxResourceUtil.getTotalMem();
        long allocated = totalMem - Constants.APP_SERVICE_RESERVED_MEMORY * Constants.G_TO_BYTES;
        log.debug("{} totalMem {},allocated {},configlimit {}",traceId,totalMem,allocated,config.getMemoryUpperLimit() * Constants.G_TO_BYTES);
        if (config.getMemoryUpperLimit() <= 0) {
            return allocated;
        } else if (config.getMemoryUpperLimit() * Constants.G_TO_BYTES > allocated) {
            return allocated;
        } else {
            return config.getMemoryUpperLimit() * Constants.G_TO_BYTES;
        }
    }

    /**
     * 设置内存上限
     * Write -1 to memory.limit_in_bytes to remove any existing limits.
     * but, if you set -1, you will see the value is 9223372036854771712. so big num
     * @param memoryCore
     * @throws IOException
     */
    private void setMemoryUsageUpperLimit(String traceId, MemoryCore memoryCore) throws IOException {
        long memoryUpperLimit = validateMemoryUpperLimitValue(traceId);
        log.debug("{} validateMemoryUpperLimitValue return {}", traceId, memoryUpperLimit);
        memoryCore.setPhysicalUsageLimit(traceId, memoryUpperLimit);
    }

    /**
     * cpu占用核数上限.
     * 如果系统可用核数小于4或者 配置为不限制的话，则返回-1；
     * 如果系统（可用核数-2）大于配置，则返回配置值，否则返回（系统可用核数-2）；
     *
     * @return
     */
    private long validateCpuUpperLimitValue() {
        int processNum = LinuxResourceUtil.getProcessNum();
        if (processNum < Constants.SYS_PROCESS_NUM_THRESHOLD || config.getCpuCoreUpperLimit() <= 0) {
            return -1L;
        } else {
            int have = processNum - Constants.APP_SERVICE_RESERVED_CPU;
            if (config.getCpuCoreUpperLimit() > have) {
                return have * Constants.CPU_UNIT;
            }
            return config.getCpuCoreUpperLimit() * Constants.CPU_UNIT;
        }
    }

    /**
     * 获取当前作业所属控制组
     * 如果资源控制粒度到作业层级，则需要重新创建控制组。
     * 当前只控制作业总量，返回根控制组即可
     *
     * @return
     */
    private CgroupCommon assembleCpuWorkerGroup() {
        CgroupCommon workerCpuGroup = new CgroupCommon(Constants.DTS_CPU_CGROUP_NAME, this.cpuHierarchy, this.cpuRootCgroup);
        return workerCpuGroup;
    }

    private CgroupCommon assembleMemoryWorkerGroup() {
        CgroupCommon workerMemoryGroup = new CgroupCommon(Constants.DTS_MEMORY_CGROUP_NAME, this.memoryHierarchy, this.memoryRootCgroup);
        return workerMemoryGroup;
    }
}
