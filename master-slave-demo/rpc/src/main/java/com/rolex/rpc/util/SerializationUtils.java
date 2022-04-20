/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.rpc.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class SerializationUtils {

    public static <T> byte[] serialize(Object in, Class<T> tClass) {
        String s = JSONObject.toJSONString(in);
        byte[] bytes = s.getBytes();
        return bytes;
    }

    public static <T> Object deserialize(byte[] data, Class<T> tClass) {
        T t = JSONObject.parseObject(new String(data), tClass);
        return t;
    }
}
