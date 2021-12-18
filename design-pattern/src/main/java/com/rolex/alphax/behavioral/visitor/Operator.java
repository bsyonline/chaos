/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
public class Operator {
    List<ResourceFile> files = new ArrayList<>();

    public void add(ResourceFile file) {
        files.add(file);
    }

    public void remove(ResourceFile file) {
        files.remove(file);
    }

    public void accept(Visitor visitor) {
        Iterator<ResourceFile> iterator = files.iterator();
        while (iterator.hasNext()) {
            iterator.next().accept(visitor);
        }
    }
}
