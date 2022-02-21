package io.github.githubob;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("io.github.githubob.spider.dao")
public class GithubObApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubObApplication.class, args);
    }

}
