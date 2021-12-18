/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class Job {
    private Context context;

    private ExecutorService executor;


    public Job(Context context) {
        this.context = context;
        // 初始化线程池
        int coreSize = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(coreSize);
    }

    public void setMapInputFormat(Class<MapInputFormat> mapInputFormat) {
        context.setMapInputFormat(mapInputFormat);
    }

    public void setMapper(Class<Mapper> mapper) {
        context.setMapper(mapper);
    }

    public void setMapOutputFormat(Class<MapOutputFormat> mapOutputFormat) {
        context.setMapOutputFormat(mapOutputFormat);
    }

    public void setPartitioner(Class<Partitioner> partitioner) {
        context.setPartitioner(partitioner);
    }


    public void setNumPartitions(int partitions) {
        context.setPartitions(partitions);
    }

    public void setSort(Class<Sort> sort) {
        context.setSort(sort);
    }

    public void setReduce(Class<Reduce> reduce) {
        context.setReduce(reduce);
    }

    public void setReduceInputFormat(Class<ReduceInputFormat> reduceInputFormat) {
        context.setReduceInputFormat(reduceInputFormat);
    }

    public void setReduceOutputFormat(Class<ReduceOutputFormat> reduceOutputFormat) {
        context.setReduceOutputFormat(reduceOutputFormat);
    }

    public void setInputPath(String path) {
        this.context.setInputDir(path);
    }

    public void setOutputPath(String path) {
        this.context.setOutputDir(path);
    }

    public void submit() throws ExecutionException, InterruptedException {
        runJob(context);
    }

    private void runJob(Context context) throws ExecutionException, InterruptedException {
        initJob(context);
        Splitter splitter = new Splitter();
        List<InputSplit> splits = splitter.getSplits(context.getInputDir());
        runMapper(context, splits);
        runShuffle(context);
        runReduce(context);
        log.info("job [{}] complete", context.getJobId());
    }

    private void runReduce(Context context) {

    }

    private void runShuffle(Context context) {

    }

    private void runMapper(Context context, List<InputSplit> splits) throws ExecutionException, InterruptedException {
        List<Future<Boolean>> futures = new ArrayList<>();
        for (InputSplit split : splits) {
            MapTaskRunner mapTaskRunner = new MapTaskRunner(context, split);
            Future future = executor.submit(mapTaskRunner);
            futures.add(future);
        }
        log.info("mapper task submit");
        checkTaskComplete(futures);
        log.info("map task complete");
    }

    private void checkTaskComplete(List<Future<Boolean>> futures) throws ExecutionException, InterruptedException {
        for (Future<Boolean> future : futures) {
            Boolean b = future.get();
        }
    }

    private void initJob(Context context) {
        String outputDir = context.getOutputDir();
        File file = new File(outputDir);
        if (file.listFiles().length != 0) {
            PathUtil.delete(outputDir);
            PathUtil.mkdir(outputDir);
        }
        String jobId = "job_" + Instant.now().getEpochSecond();
        context.setJobId(jobId);
    }
}
