package com.rolex.microservices.controller;

import com.rolex.microservices.api.HelloService;
import com.rolex.microservices.comsumer.HelloServiceImpl;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
public class HelloController {
    @DubboReference(version = "1.0.0")
    private HelloService helloService;

    @RequestMapping("/hello/{id}")
    public String sayHello(@PathVariable Long id) {
        return helloService.sayHello(id);
    }
}
