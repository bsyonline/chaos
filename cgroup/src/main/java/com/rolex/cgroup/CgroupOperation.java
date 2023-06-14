package com.rolex.cgroup;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public interface CgroupOperation {
    /**
     * 获取所有层级树
     *
     * @return
     */
    List<Hierarchy> getHierarchies();

    /**
     * 获取所有子系统
     *
     * @return
     */
    Set<SubSystem> getSubSystems();

    /**
     * 子系统是否启用
     *
     * @param subsystem
     * @return
     */
    boolean enabled(SubSystemType subsystem);

    /**
     * 获取子系统已经被关联的层级树
     *
     * @param subsystem
     * @return
     */
    Hierarchy busy(String traceId, SubSystemType subsystem);

    /**
     * 校验层级树是否已挂载
     *
     * @param hierarchy
     * @return
     */
    Hierarchy mounted(String traceId, Hierarchy hierarchy);

    /**
     * 挂载层级树
     *
     * @param hierarchy
     * @throws IOException
     */
    void mount(String traceId, Hierarchy hierarchy) throws IOException;

    /**
     * 解挂层级树
     *
     * @param hierarchy
     * @throws IOException
     */
    void umount(String traceId, Hierarchy hierarchy) throws IOException;

    void create(String traceId, CgroupCommon cgroup) throws SecurityException;

    void delete(String traceId, CgroupCommon cgroup) throws IOException;
}
