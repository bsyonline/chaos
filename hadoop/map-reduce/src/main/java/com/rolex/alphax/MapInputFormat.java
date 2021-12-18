/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;

import lombok.Getter;
import lombok.Setter;

import java.io.*;

/**
 * @author rolex
 * @since 2020
 */
@Setter
@Getter
public class MapInputFormat {
    private long key;
    private String value;
    private String blockPath;
    private BufferedReader bufferedReader;

    public MapInputFormat(String blockPath) throws FileNotFoundException {
        this.blockPath = blockPath;
        this.bufferedReader = initReader(blockPath);
    }

    private BufferedReader initReader(String blockPath) throws FileNotFoundException {
        key = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(blockPath)));
        return br;
    }

    public boolean nextKeyValue() {
        return false;
    }

    public Object getCurrentKey() {
        return null;
    }

    public boolean getCurrentValue() throws IOException {
        value = bufferedReader.readLine();
        if (value == null) {
            close();
            return false;
        } else {
            key += value.length();
            return true;
        }

    }

    private void close() {


    }
}
