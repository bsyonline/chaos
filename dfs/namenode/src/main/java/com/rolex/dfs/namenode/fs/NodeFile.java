package com.rolex.dfs.namenode.fs;

import com.rolex.dfs.namenode.fs.INode;
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
public class NodeFile implements INode {
    private String name;
}
