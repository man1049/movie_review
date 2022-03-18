package com.naver.man10_49.movie_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MovieWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieWebApplication.class, args);
    }
/*
    @Bean
    public HttpSessionListener httpSessionListener(){
        return new LoginSessionListener();
    }
*/
}
