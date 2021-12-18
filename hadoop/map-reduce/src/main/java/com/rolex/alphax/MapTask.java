/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class MapTask {
    public void runMapTask(InputSplit split, Context context) throws FileNotFoundException, IllegalAccessException, InstantiationException {
        String taskId = initTask(split);
        log.info("task initialized, taskId={}", taskId);
        MapInputFormat mapInputFormat = new MapInputFormat(split.getBlockPath());
        String tmpPath = context.getConfig("map-reduce.path");
        MapOutputFormat mapOutputFormat = new MapOutputFormat(tmpPath, context, taskId);
        Mapper mapper = context.getMapper().newInstance();
        mapper.run(mapInputFormat, mapOutputFormat);

    }

    private String initTask(InputSplit split) {
        return "task_" + split.getBlockId();
    }
}
