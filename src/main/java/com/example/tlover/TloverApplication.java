package com.example.tlover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TloverApplication {

    public static void main(String[] args) {
        SpringApplication.run(TloverApplication.class, args);
    }

}
