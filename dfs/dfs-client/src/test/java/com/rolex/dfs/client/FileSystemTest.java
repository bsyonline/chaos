package com.rolex.dfs.client;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class FileSystemTest {
    public static void main(String[] args) throws InterruptedException {
        FileSystem fileSystem = new FileSystem();
        fileSystem.mkdir("/usr/warehouse/hive");
        fileSystem.mkdir("/usr/warehouse/spark");
        fileSystem.mkdir("/data/project");
        fileSystem.mkdir("/logs/common/success.log");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
