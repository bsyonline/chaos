/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.adapter.objectadapter;

/**
 * @author rolex
 * @since 2020
 */
public class OuterUserAdapter implements IUserInfo {

    private IOuterUserInfo outerUserInfo;

    public OuterUserAdapter(IOuterUserInfo outerUserInfo) {
        this.outerUserInfo = outerUserInfo;
    }

    @Override
    public String getName() {
        return (String) outerUserInfo.basicInfo().get("name");
    }

    @Override
    public Integer getAge() {
        return Integer.parseInt((String) outerUserInfo.basicInfo().get("age"));
    }

    @Override
    public String getTechnical() {
        return (String) outerUserInfo.technicalInfo().get("language");
    }
}
