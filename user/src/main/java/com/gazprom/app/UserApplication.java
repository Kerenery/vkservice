package com.gazprom.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
public class UserApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(UserApplication.class, args);
    }
}
