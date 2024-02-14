package com.example.log_puller;

import com.example.log_puller.utility.PropertyReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class LogPullerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogPullerApplication.class, args);
    }
}
