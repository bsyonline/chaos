/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author rolex
 * @since 2020
 */
@Setter
@Getter
public class Context {
    private String jobId;
    private Class<MapInputFormat> mapInputFormat;
    private Class<MapOutputFormat> mapOutputFormat;
    private Class<Mapper> mapper;
    private Class<Partitioner> partitioner;
    private Class<Sort> sort;
    private Class<Reduce> reduce;
    private Class<ReduceInputFormat> reduceInputFormat;
    private Class<ReduceOutputFormat> reduceOutputFormat;
    private int partitions;
    private String inputDir;
    private String outputDir;
    private Properties properties;
    private List<String> mapTaskTempDirs;

    public Context() throws IOException {
        this.properties = PropertiesUtil.loadProperties();
        this.mapTaskTempDirs = new ArrayList<>();
    }

    public String getConfig(String key){
        return (String) this.properties.get(key);
    }
}
