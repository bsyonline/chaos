/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;

import com.google.gson.Gson;
import com.rolex.alphax.model.Response;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootApplication
@RestController
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @PostMapping("/api/auth")
    public Response auth(@RequestBody Map<String, String> map) {
        String requestURI = map.get("requestURI");
        User user = new User();
        Map result = new HashMap();
        if (requestURI.endsWith("2")) {
            return new Response(401, "请求未授权");
        }
        user.setId(1L);
        return new Response(200, "成功", user);
    }

    @Data
    class User {
        Long id;
    }
}
