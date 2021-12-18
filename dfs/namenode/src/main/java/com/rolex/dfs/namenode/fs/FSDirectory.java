package com.rolex.dfs.namenode.fs;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class FSDirectory {
    private NodeDirectory dirTree;

    public FSDirectory() {
        this.dirTree = new NodeDirectory("/");
    }

    public void mkdir(String path) {
        synchronized (dirTree) {
            String[] paths = path.split("/");
            NodeDirectory parent = dirTree;
            for (String splitedPath : paths) {
                if (splitedPath.trim().equals("")) {
                    continue;
                }
                NodeDirectory dir = findDirectory(parent, splitedPath);
                if (dir != null) {
                    parent = dir;
                    continue;
                }
                NodeDirectory child = new NodeDirectory(splitedPath);
                parent.addChild(child);
                parent = child;
            }
            printDirTree(dirTree, "");
        }
    }

    private void printDirTree(INode iNode, String blank) {
        if (iNode instanceof NodeDirectory) {
            NodeDirectory nodeDirectory = (NodeDirectory) iNode;
            for (INode node : nodeDirectory.getChildren()) {
                print(blank, node);
                printDirTree(node, blank + "  ");
            }
        } else {
            print(blank, iNode);
        }
    }

    private void print(String blank, INode node) {
        if (node instanceof NodeDirectory) {
            System.out.println(blank + ((NodeDirectory) node).getPath());
        } else {
            System.out.println(blank + ((NodeFile) node).getName());
        }
    }

    private NodeDirectory findDirectory(NodeDirectory dir, String path) {
        if (dir.getChildren().size() == 0) {
            return null;
        }
        for (INode child : dir.getChildren()) {
            if (child instanceof NodeDirectory) {
                NodeDirectory childDir = (NodeDirectory) child;
                if ((childDir.getPath().equals(path))) {
                    return childDir;
                }
            }
        }
        return null;
    }

}
