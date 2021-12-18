package com.rolex.dfs.namenode.wal;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class FSEditLog {
    /**
     * 当前递增到的txid的序号
     */
    private long txIdSeq = 0L;
    /**
     * 内存双缓冲区
     */
    private DoubleBuffer editLogBuffer = new DoubleBuffer();
    /**
     * 当前是否在将内存缓冲刷入磁盘中
     */
    private volatile Boolean isSyncRunning = false;
    /**
     * 当前是否有线程在等待刷新下一批edits log到磁盘里去
     */
    private volatile Boolean isWaitSync = false;
    /**
     * 在同步到磁盘中的最大的一个txid
     */
    private volatile Long syncMaxTxId = 0L;
    /**
     * 每个线程自己本地的txid副本
     */
    private ThreadLocal<Long> localTxId = new ThreadLocal<Long>();

    public void logEdit(String content) {
        // 这里必须得直接加锁
        synchronized(this) {
            // 获取全局唯一递增的txid，代表了edits log的序号
            txIdSeq++;
            long txId = txIdSeq;
            localTxId.set(txId); // 放到ThreadLocal里去，相当于就是维护了一份本地线程的副本

            // 构造一条edits log对象
            EditLog log = new EditLog(txId, content);

            // 将edits log写入内存缓冲中，不是直接刷入磁盘文件
            editLogBuffer.write(log);
        }

        logSync();
    }

    private void logSync() {
        // 再次尝试加锁
        synchronized(this) {
            // 如果说当前正好有人在刷内存缓冲到磁盘中去
            if(isSyncRunning) {
                // 那么此时这里应该有一些逻辑判断

                // 假如说某个线程已经把txid = 1,2,3,4,5的edits log都从syncBuffer刷入磁盘了
                // 或者说此时正在刷入磁盘中
                // 此时syncMaxTxid = 5，代表的是正在输入磁盘的最大txid
                // 那么这个时候来一个线程，他对应的txid = 3，此时他是可以直接返回了
                // 就代表说肯定是他对应的edits log已经被别的线程在刷入磁盘了
                // 这个时候txid = 3的线程就不需要等待了
                long txid = localTxId.get(); // 获取到本地线程的副本
                if(txid <= syncMaxTxId) {
                    return;
                }

                // 此时再来一个txid = 9的线程的话，那么他会发现说，已经有线程在等待刷下一批数据到磁盘了
                // 此时他会直接返回
                // 假如说此时来一个txid = 6的线程，那么的话，他是不好说的
                // 他就需要做一些等待，同时要释放掉锁
                if(isWaitSync) {
                    return;
                }
                // 比如说此时可能是txid = 15的线程在这里等待
                isWaitSync = true;
                while(isSyncRunning) {
                    try {
                        wait(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isWaitSync = false;
            }

            // 交换两块缓冲区
            editLogBuffer.setReadyToSync();
            // 然后可以保存一下当前要同步到磁盘中去的最大的txid
            // 此时editLogBuffer中的syncBuffer这块区域，交换完以后这里可能有多条数据
            // 而且他里面的edits log的txid一定是从小到大的
            // 此时要同步的txid = 6,7,8,9,10,11,12
            // syncMaxTxid = 12
            syncMaxTxId = editLogBuffer.getSyncMaxTxId();
            // 设置当前正在同步到磁盘的标志位
            isSyncRunning = true;
        }

        // 开始同步内存缓冲的数据到磁盘文件里去
        // 这个过程其实是比较慢，基本上肯定是毫秒级了，弄不好就要几十毫秒
        editLogBuffer.flush();

        synchronized(this) {
            // 同步完了磁盘之后，就会将标志位复位，再释放锁
            isSyncRunning = false;
            // 唤醒可能正在等待他同步完磁盘的线程
            notifyAll();
        }
    }
}
