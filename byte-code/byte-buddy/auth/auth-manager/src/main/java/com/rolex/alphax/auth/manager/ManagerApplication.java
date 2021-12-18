package com.rolex.alphax.auth.manager;

import com.rolex.alphax.model.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }

    @GetMapping("/api/users/{id}")
    public Response test(@PathVariable("id") Long id) {
        return new Response(200, "成功", "user" + id);
    }

}