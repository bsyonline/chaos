package com.rolex.cgroup;

import java.util.Objects;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public enum SubSystemType {
    /**
     * cpu子系统
     */
    cpu,
    /**
     * 内存子系统
     */
    memory;

    public static SubSystemType getSubSystem(String type) {
        for (SubSystemType subSystemType : SubSystemType.values()) {
            if (Objects.equals(subSystemType.name(), type)) {
                return subSystemType;
            }
        }
        return null;
    }
}
