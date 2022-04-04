package io.github.githubob;

import io.github.githubob.spider.dao.ContributorDao;
import io.github.githubob.spider.service.impl.UserReposServiceImpl;
import io.github.githubob.spider.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@SpringBootTest(classes = GithubObApplication.class)
public class SpiderTest {
    @Resource
    private UserReposServiceImpl repoService;
    @Resource
    private UserServiceImpl userService;
    @Resource
    private Jedis jedisTemplate;
    @Resource
    private ContributorDao contributorDao;
    @Test
    public void run(){
        RestTemplate restTemplate=new RestTemplateBuilder().basicAuthentication("3177b31e134ddcbd9a48","5e17f09e1fed5801710c2e32445c7fc24c74c021").build();

    }
}
