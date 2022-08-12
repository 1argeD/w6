package com.sparta.w6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class W6Application {

    public static void main(String[] args) {
        SpringApplication.run(W6Application.class, args);
    }

}
