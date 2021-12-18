/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class MapTaskRunner implements Callable {

    private InputSplit split;
    private Context context;
    private MapTask mapTask;

    public MapTaskRunner(Context context, InputSplit split) {
        this.context = context;
        this.split = split;
        mapTask = new MapTask();
    }

    @Override
    public Object call() throws Exception {
        log.info("map task start");
        mapTask.runMapTask(split, context);
        return true;
    }
}
