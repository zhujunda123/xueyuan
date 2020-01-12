package com.online.edu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import util.JwtUtil;

@SpringBootApplication
public class EduServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduServiceApplication.class,args);
    }

    @Bean
    public JwtUtil jwtUtil(){ return new util.JwtUtil(); }
}

