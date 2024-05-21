package com.example.demo.skywalking.skywalking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SkywalkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkywalkingApplication.class, args);
    }

}
