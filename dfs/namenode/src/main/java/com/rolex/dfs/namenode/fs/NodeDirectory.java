package com.rolex.dfs.namenode.fs;

import com.rolex.dfs.namenode.fs.INode;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Data
public class NodeDirectory implements INode {

    private String path;
    private List<INode> children;

    public NodeDirectory(String path) {
        this.path = path;
        this.children = new LinkedList<INode>();
    }

    public void addChild(INode inode) {
        this.children.add(inode);
    }

}
