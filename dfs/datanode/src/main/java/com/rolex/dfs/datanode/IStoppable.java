package com.rolex.dfs.datanode;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public interface IStoppable {

    /**
     * Stop this service.
     * @param cause why stopping
     */
    void stop(String cause);
}
