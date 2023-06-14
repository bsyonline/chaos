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
public class SubSystem {
    /**
     * 子系统类型
     */
    private SubSystemType type;

    /**
     * 层级树ID
     */
    private int hierarchyId;

    /**
     * 控制组数量
     */
    private int cgroupsNum;

    /**
     * 是否启用
     */
    private boolean enable;

    /**
     * @param type        子系统类型
     * @param hierarchyId 层级树ID
     * @param cgroupNum   控制组数量
     * @param enable      是否启用
     */
    public SubSystem(SubSystemType type, int hierarchyId, int cgroupNum, boolean enable) {
        this.type = type;
        this.hierarchyId = hierarchyId;
        this.cgroupsNum = cgroupNum;
        this.enable = enable;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + hierarchyId;
        result = prime * result + cgroupsNum;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
