/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.adapter.classadapter;

/**
 * @author rolex
 * @since 2020
 */
public class UserInfo implements IUserInfo {
    @Override
    public String getName() {
        return "tom";
    }

    @Override
    public Integer getAge() {
        return 20;
    }

    @Override
    public String getTechnical() {
        return "java";
    }
}
