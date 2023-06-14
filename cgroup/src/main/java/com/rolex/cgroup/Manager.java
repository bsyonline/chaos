package com.rolex.cgroup;

import java.io.IOException;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public interface Manager {

    /**
     * STARTING A PROCESS IN A CONTROL GROUP
     *
     * @param traceId
     * @param jobInstId
     * @return
     */
    String startJobInstance(String traceId, Long jobInstId);

    /**
     * @param traceId
     * @throws IOException
     */
    void createIfAbsentWorkerRootCgroup(String traceId) throws IOException;

    /**
     * cgroup 是否可用
     *
     * @return
     */
    boolean enable();

    void reloadCgroupConfig(String traceId) throws IOException;

}
