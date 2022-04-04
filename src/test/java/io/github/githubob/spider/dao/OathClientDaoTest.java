package io.github.githubob.spider.dao;

import io.github.githubob.GithubObApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = GithubObApplication.class)
public class OathClientDaoTest {
    @Resource
    private OauthClientDao oauthClientDao;
    @Test
    public void getAll(){
        System.out.println(oauthClientDao.getAllOauthClient());
    }
}
