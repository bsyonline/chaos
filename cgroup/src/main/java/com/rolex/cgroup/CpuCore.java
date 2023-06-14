package com.rolex.cgroup;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
public class CpuCore implements CgroupCore {
    public static final String CPU_SHARES = "/cpu.shares";
    public static final String CPU_CFS_PERIOD_US = "/cpu.cfs_period_us";
    public static final String CPU_CFS_QUOTA_US = "/cpu.cfs_quota_us";

    private final String dir;

    public CpuCore(String dir) {
        this.dir = dir;
    }

    @Override
    public SubSystemType getType() {
        return SubSystemType.cpu;
    }

    public void setCpuShares(int weight) throws IOException {
        CgroupUtils.writeFileByLine(ParseUtil.getDir(this.dir, CPU_SHARES), String.valueOf(weight));
    }

    public int getCpuShares() throws IOException {
        return Integer.parseInt(CgroupUtils.readFileByLine(ParseUtil.getDir(this.dir, CPU_SHARES)).get(0));
    }

    public void setCpuCfsPeriodUs(long us) throws IOException {
        CgroupUtils.writeFileByLine(ParseUtil.getDir(this.dir, CPU_CFS_PERIOD_US), String.valueOf(us));
    }

    public Long getCpuCfsPeriodUs() throws IOException {
        return Long.parseLong(CgroupUtils.readFileByLine(ParseUtil.getDir(this.dir, CPU_CFS_PERIOD_US)).get(0));
    }

    public void setCpuCfsQuotaUs(String traceId, long us) throws IOException {
        String filedir = ParseUtil.getDir(this.dir, CPU_CFS_QUOTA_US);
        log.debug("{} setCpuCfsQuotaUs filedir {},value {}", traceId, filedir, us);
        CgroupUtils.writeFileByLine(filedir, String.valueOf(us));
    }

    public Long getCpuCfsQuotaUs() throws IOException {
        return Long.parseLong(CgroupUtils.readFileByLine(ParseUtil.getDir(this.dir, CPU_CFS_QUOTA_US)).get(0));
    }
}