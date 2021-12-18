package com.rolex.dfs.namenode.wal;

import java.util.LinkedList;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class DoubleBuffer {
    /**
     * 是专门用来承载线程写入edits log
     */
    LinkedList<EditLog> currentBuffer = new LinkedList<EditLog>();
    /**
     * 专门用来将数据同步到磁盘中去的一块缓冲
     */
    LinkedList<EditLog> syncBuffer = new LinkedList<EditLog>();
    /**
     * 将edits log写到内存缓冲里去
     * @param log
     */
    public void write(EditLog log) {
        currentBuffer.add(log);
    }

    /**
     * 交换两块缓冲区，为了同步内存数据到磁盘做准备
     */
    public void setReadyToSync() {
        LinkedList<EditLog> tmp = currentBuffer;
        currentBuffer = syncBuffer;
        syncBuffer = tmp;
    }

    /**
     * 获取sync buffer缓冲区里的最大的一个txid
     * @return
     */
    public Long getSyncMaxTxId() {
        return syncBuffer.getLast().txId;
    }

    /**
     * 将syncBuffer缓冲区中的数据刷入磁盘中
     */
    public void flush() {
        for(EditLog log : syncBuffer) {
            System.out.println("将edit log写入磁盘文件中：" + log);
            // 正常来说，就是用文件输出流将数据写入磁盘文件中
        }
        syncBuffer.clear();
    }
}
