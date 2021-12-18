/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author rolex
 * @since 2020
 */
public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Context context = new Context();

        Job job = new Job(context);
        job.setMapInputFormat(MapInputFormat.class);
        job.setMapper(Mapper.class);
        job.setMapOutputFormat(MapOutputFormat.class);

        job.setPartitioner(Partitioner.class);
        job.setNumPartitions(3);
        job.setSort(Sort.class);

        job.setReduceInputFormat(ReduceInputFormat.class);
        job.setReduce(Reduce.class);
        job.setReduceOutputFormat(ReduceOutputFormat.class);

        job.setInputPath("D:/tmp/map-reduce/word.txt");
        job.setOutputPath("D:/tmp/map-reduce");

        job.submit();

        System.exit(0);
    }
}
