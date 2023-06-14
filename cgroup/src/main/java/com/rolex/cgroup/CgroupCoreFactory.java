package com.rolex.cgroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class CgroupCoreFactory {
    public static Map<SubSystemType, CgroupCore> getInstance(Set<SubSystemType> types, String dir) {
        Map<SubSystemType, CgroupCore> result = new HashMap<SubSystemType, CgroupCore>();
        for (SubSystemType type : types) {
            switch (type) {
                case cpu:
                    result.put(SubSystemType.cpu, new CpuCore(dir));
                    break;
                case memory:
                    result.put(SubSystemType.memory, new MemoryCore(dir));
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}
