package com.rolex.cgroup;

import java.io.IOException;
import java.util.Set;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public interface CgroupCommonOperation {
    void addTask(int taskId) throws IOException;

    Set<Integer> getTasks() throws IOException;

    void addProcs(int pid) throws IOException;

    Set<Integer> getPids() throws IOException;
}
