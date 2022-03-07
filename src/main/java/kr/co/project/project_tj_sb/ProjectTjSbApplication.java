package kr.co.project.project_tj_sb;

import org.apache.catalina.SessionEvent;
import org.apache.catalina.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
@EnableJpaAuditing
public class ProjectTjSbApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectTjSbApplication.class, args);
    }
/*
    @Bean
    public HttpSessionListener httpSessionListener(){
        return new LoginSessionListener();
    }
*/
}
