/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;

import lombok.Getter;
import lombok.Setter;

/**
 * @author rolex
 * @since 2020
 */
@Setter
@Getter
public class InputSplit {

    private String blockId;
    private String blockName;
    private String blockPath;
    private String node;

    public InputSplit(String name, String path) {
        this.blockId = name;
        this.blockPath = path;
    }
}
