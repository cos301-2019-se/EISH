package com.monotoneid.eishms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *CLASS EISHMS. 
 */
@SpringBootApplication
@EnableScheduling
public class EishmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EishmsApplication.class, args);
    } 
}
