/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.adapter.objectadapter;

/**
 * 对象适配器：通过组合 OuterUserInfo
 * 内部用户接口（IUserInfo）使用对象存储用户信息，外部用户接口（IOuterUserInfo）使用Map存储用户信息。
 * 内部程序使用外部用户数据，需要使用适配器（OuterUserAdapter）进行适配
 *
 * @author rolex
 * @since 2020
 */
public class App {
    public static void main(String[] args) {
        UserService userService = new UserService(new UserInfo());
        userService.getUserInfo();
        userService = new UserService(new OuterUserAdapter(new OuterUserInfo()));
        userService.getUserInfo();
    }
}
