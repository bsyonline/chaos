/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.combinator;

import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
public class TextFinder implements Finder{
    @Override
    public List<String> find(String text) {
        return null;
    }

    @Override
    public Finder not(Finder notFinder) {
        return new Finder() {
            @Override
            public List<String> find(String text) {
                List<String> res = this.find(text);
                res.removeAll(notFinder.find(text));
                return res;
            }
        };
    }

    @Override
    public Finder or(Finder orFinder) {
        return null;
    }

    @Override
    public Finder and(Finder andFinder) {
        return null;
    }
}
