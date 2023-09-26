package com.rolex.event;

import com.rolex.event.demo.ModelServiceImplV1;
import com.rolex.event.demo.ModelServiceImplV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author rolex
 * @since 2023/7/1
 */
@SpringBootApplication
public class App implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    ModelServiceImplV1 modelServiceImplV1;
    @Autowired
    ModelServiceImplV2 modelServiceImplV2;

    @Override
    public void run(String... args) throws Exception {
        modelServiceImplV1.buildModelInstance("test");
        System.out.println("--");
        modelServiceImplV2.buildModelInstance("test");
    }
}
