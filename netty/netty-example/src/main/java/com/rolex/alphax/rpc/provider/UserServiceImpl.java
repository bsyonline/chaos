/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.rpc.provider;

import com.rolex.alphax.rpc.common.User;
import com.rolex.alphax.rpc.common.UserService;

/**
 * @author rolex
 * @since 2020
 */
public class UserServiceImpl implements UserService {
    static int count = 0;

    @Override
    public String getUser(String id) {
        System.out.println("第" + (++count) + "次收到客户端消息：" + id);
        return new User(1, "Tom", 20).toString();
    }
}
