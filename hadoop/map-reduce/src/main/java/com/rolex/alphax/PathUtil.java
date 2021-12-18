/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;

import java.io.File;
import java.text.DecimalFormat;

/**
 * @author rolex
 * @since 2020
 */
public class PathUtil {
    public static void delete(String outputDir) {

    }

    public static void mkdir(String dir) {
// 当前目录
        File currentDir = new File(dir);

        // 父目录
        File parentDir = currentDir.getParentFile();

        // 如果不存在，递归创建
        if (!parentDir.exists()) {
            mkdir(currentDir.getParent());
        }
        currentDir.mkdir();
    }

    public static String checkPath(String s) {

        return s;
    }

    public static String genMapOutputTmpFileName(int partitionNum) {
        return "part_m_" + validatePartitionNum(partitionNum);
    }

    private static String validatePartitionNum(int partitionNum) {
        DecimalFormat decimalFormat = new DecimalFormat("00000");
        return decimalFormat.format(partitionNum);
    }
}
