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
public class MemoryCore implements CgroupCore {

    public static final String MEMORY_LIMIT_IN_BYTES = "/memory.limit_in_bytes";

    private final String dir;

    public MemoryCore(String dir) {
        this.dir = dir;
    }

    @Override
    public SubSystemType getType() {
        return SubSystemType.memory;
    }

    public void setPhysicalUsageLimit(String traceId, long value) throws IOException {
        String filedir = ParseUtil.getDir(this.dir, MEMORY_LIMIT_IN_BYTES);
        log.debug("{} setPhysicalUsageLimit filedir {},value {}", traceId, filedir, value);
        CgroupUtils.writeFileByLine(filedir, String.valueOf(value));
    }

    public long getPhysicalUsageLimit() throws IOException {
        return Long.parseLong(CgroupUtils.readFileByLine(ParseUtil.getDir(this.dir, MEMORY_LIMIT_IN_BYTES)).get(0));
    }
}