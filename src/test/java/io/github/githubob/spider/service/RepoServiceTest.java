package io.github.githubob.spider.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.githubob.GithubObApplication;
import io.github.githubob.spider.entity.Param;
import io.github.githubob.spider.service.impl.RepoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = GithubObApplication.class)
public class RepoServiceTest {
    @Resource
    private RepoServiceImpl repoService;
    @Test
    public void addRepo() throws JsonProcessingException {
        repoService.saveData(Param.builder().user("Snailclimb").repo("JavaGuide").build());
    }
}
