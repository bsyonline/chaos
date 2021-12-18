/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.adapter.objectadapter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class UserService {

    private IUserInfo userInfo;

    public UserService(IUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    void getUserInfo() {
        log.info("name={}, age={}, technical={}", userInfo.getName(), userInfo.getAge(), userInfo.getTechnical());
    }

}
