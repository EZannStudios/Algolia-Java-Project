package com.challenge.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ChallengeApplication {
    public static void main(final String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }
}
