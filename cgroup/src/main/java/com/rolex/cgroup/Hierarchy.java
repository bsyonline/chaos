package com.rolex.cgroup;

import java.util.Set;

/**
 * <P>
 * 层级树。hierarchy 由一系列 cgroup 以一个树状结构排列而成，每个 hierarchy 通过绑定对应的 subsystem 进行资源调度。
 * hierarchy 中的 cgroup 节点可以包含零或多个子节点，子节点继承父节点的属性。
 * 整个系统可以有多个 hierarchy
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class Hierarchy {
    /**
     * 名称
     */
    private final String name;

    /**
     * 层级树关联子系统集合
     */
    private final Set<SubSystemType> subSystems;

    /**
     * 层级树关联系统类型字符串表示形式
     */
    private final String type;

    /**
     * 层级树目录，即mount的路径
     */
    private final String dir;

    private final CgroupCommon rootCgroups;

    public Hierarchy(String name, Set<SubSystemType> subSystems, String dir) {
        this.name = name;
        this.subSystems = subSystems;
        this.dir = dir;
        this.rootCgroups = new CgroupCommon(this, dir);
        this.type = CgroupUtils.reAnalyse(subSystems);
    }

    public Set<SubSystemType> getSubSystems() {
        return subSystems;
    }

    public String getType() {
        return type;
    }

    public String getDir() {
        return dir;
    }

    public CgroupCommon getRootCgroups() {
        return rootCgroups;
    }

    public String getName() {
        return name;
    }

    public boolean subSystemMounted(SubSystemType subsystem) {
        for (SubSystemType type : this.subSystems) {
            if (type == subsystem) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dir == null) ? 0 : dir.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Hierarchy other = (Hierarchy) obj;
        if (dir == null) {
            if (other.dir != null) {
                return false;
            }
        } else if (!dir.equals(other.dir)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }
}
