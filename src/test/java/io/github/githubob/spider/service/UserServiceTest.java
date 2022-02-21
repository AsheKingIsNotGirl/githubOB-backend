package io.github.githubob.spider.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.githubob.GithubObApplication;
import io.github.githubob.spider.entity.Param;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = GithubObApplication.class)
public class UserServiceTest {
    @Resource
    private AbstrastSpiderService userService;
    @Test
    public void addRepo() throws JsonProcessingException {
        userService.saveData(Param.builder().user("Snailclimb").build());
    }
}
