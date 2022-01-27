package kr.co.project.project_tj_sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjectTjSbApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectTjSbApplication.class, args);
    }

}
