/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms5m -Xmx5m -XX:+PrintGCDetails
 *
 * @author rolex
 * @since 2020
 */
public class GCDetailsExample {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(new byte[20 * 1024 * 1024]);

    }
}
