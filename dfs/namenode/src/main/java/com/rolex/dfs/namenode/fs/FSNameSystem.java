package com.rolex.dfs.namenode.fs;

import com.rolex.dfs.namenode.wal.FSEditLog;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class FSNameSystem {
    /**
     * 负责管理内存文件目录树的组件
     */
    private FSDirectory directory;
    /**
     * 负责管理edits log写入磁盘的组件
     */
    private FSEditLog editLog;

    public FSNameSystem() {
        this.directory = new FSDirectory();
        this.editLog = new FSEditLog();
    }

    public Boolean mkdir(String path) throws Exception {
        this.directory.mkdir(path);
        this.editLog.logEdit("创建了一个目录：" + path);
        return true;
    }

}
