/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;



/**
 * @author rolex
 * @since 2020
 */
public class Mapper {
    public void run(MapInputFormat mapInputFormat, MapOutputFormat mapOutputFormat) {
        setup(mapInputFormat, mapOutputFormat);
        try {
            while (mapInputFormat.nextKeyValue()) {
                map(mapInputFormat.getCurrentKey(), mapInputFormat.getCurrentValue(), mapOutputFormat);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cleanup(mapInputFormat, mapOutputFormat);
        }
    }
    protected void map(Object currentKey, Object currentValue, MapOutputFormat mapOutputFormat){
        // 编写业务逻辑
        String value = (String) currentValue;
        String[] words = value.split(" ");

        for (String word : words) {
            mapOutputFormat.write(word, 1);
        }
    }
    protected void cleanup(MapInputFormat mapInputFormat, MapOutputFormat mapOutputFormat) {

        mapOutputFormat.close();
    }
    private void setup(MapInputFormat mapInputFormat, MapOutputFormat mapOutputFormat) {

    }
}
