package com.rolex.discovery.routing;

import lombok.Data;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Data
public class NodeId {
    int nodeId;

    private NodeId(int nodeId){
        this.nodeId = nodeId;
    }

    public static NodeId of(int nodeId){
        return new NodeId(nodeId);
    }
}
