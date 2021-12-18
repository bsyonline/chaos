/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;

import com.sun.prism.impl.Disposer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
public class MapOutputFormat {
    private String outputPath;
    private String jobId;
    private String taskId;

    private List<PrintWriter> writers;
    private Partitioner partitioner;
    private int partitionNum;
    private Sort sort;
    private List<ArrayList<Record>> recordsList;

    public MapOutputFormat(String outputPath, Context context, String taskId) throws FileNotFoundException, InstantiationException, IllegalAccessException {
        initialize(context, taskId);
        String jobId = context.getJobId();
        String path = PathUtil.checkPath(outputPath + File.separator + jobId + File.separator + taskId);
        File file = new File(path);
        if (!file.exists()) {
            PathUtil.mkdir(path);
        }
        context.getMapTaskTempDirs().add(path);

        int partitions = context.getPartitions();
        writers = new ArrayList<>(partitions);

        for (int i = 0; i < partitions; i++) {
            String tmpPath = path + File.separator + PathUtil.genMapOutputTmpFileName(i) + ".txt";
            tmpPath = PathUtil.checkPath(tmpPath);
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(tmpPath));
            writers.add(printWriter);
        }
    }

    private void initialize(Context context, String taskId) throws IllegalAccessException, InstantiationException {
        this.taskId = taskId;
        this.partitionNum = context.getPartitions();
        this.partitioner = context.getPartitioner().newInstance();
        this.recordsList = new ArrayList<>(partitionNum);
        recordsList.add(new ArrayList<>());
        this.sort = context.getSort().newInstance();
    }

    public void write(String word, int i) {

    }

    public void close() {

    }
}
