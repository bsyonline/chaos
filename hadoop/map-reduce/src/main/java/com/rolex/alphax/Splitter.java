/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
public class Splitter {

    public List<InputSplit> getSplits(String inputDir) {
        List<InputSplit> splits = new ArrayList<>();
        File file = new File(inputDir);
        if (file.isFile()) {
            splits.add(new InputSplit(file.getName(), file.getPath()));
        } else {
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                splits.add(new InputSplit(subFile.getName(), subFile.getPath()));
            }
        }
        return splits;
    }
}
