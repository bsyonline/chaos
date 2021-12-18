/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.service.impl;

import com.rolex.alphax.model.User;
import com.rolex.alphax.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rolex
 * @since 2020
 */
@Component
public class UserServiceImpl implements UserService {

    public static Map<Integer, User> map = new HashMap<>();

    static{
        map.put(1, new User(1, "Marry", 20));
        map.put(2, new User(2, "Jack", 19));
        map.put(3, new User(3, "Alice", 23));
        map.put(4, new User(4, "John", 18));
        map.put(5, new User(5, "Kate", 21));
        map.put(6, new User(6, "Bob", 22));
        map.put(7, new User(7, "Jane", 24));
        map.put(8, new User(8, "Pony", 25));
        map.put(9, new User(9, "Grace", 21));
        map.put(10, new User(10, "Tom", 20));
    }

    @Override
    public User getUser(int id) {
        return  map.get(id);
    }
}
