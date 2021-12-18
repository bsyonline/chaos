/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.adapter.objectadapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rolex
 * @since 2020
 */
public class OuterUserInfo implements IOuterUserInfo {

    @Override
    public Map basicInfo() {
        Map basicInfo = new HashMap(16);
        basicInfo.put("name", "alice");
        basicInfo.put("age", "22");
        return basicInfo;
    }

    @Override
    public Map technicalInfo() {
        Map technicalInfo = new HashMap(16);
        technicalInfo.put("language", "c++");
        return technicalInfo;
    }
}
