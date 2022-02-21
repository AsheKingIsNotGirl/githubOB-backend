package io.github.githubob.spider.init;

import io.github.githubob.spider.dao.ContributorDao;
import io.github.githubob.spider.scheduler.ContributorScheduler;
import io.github.githubob.spider.scheduler.UsedRepoScheduler;
import io.github.githubob.spider.scheduler.UserScheduler;
import io.github.githubob.spider.service.impl.RepoServiceImpl;
import io.github.githubob.spider.service.impl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Component
public class SpiderRunner implements CommandLineRunner {
    @Resource
    private RepoServiceImpl repoService;
    @Resource
    private UserServiceImpl userService;
    @Resource
    private Jedis jedisTemplate;
    @Resource
    private ContributorDao contributorDao;
    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate=new RestTemplateBuilder().basicAuthentication("3177b31e134ddcbd9a48","5e17f09e1fed5801710c2e32445c7fc24c74c021").build();

        ExecutorService pool = Executors.newFixedThreadPool(3);
        ContributorScheduler contributorScheduler = ContributorScheduler.builder().contributorDao(contributorDao).restTemplate(restTemplate).build();
        pool.execute(contributorScheduler);
        UsedRepoScheduler usedRepoScheduler = UsedRepoScheduler.builder().repoService(repoService).restTemplate(restTemplate).build();
        pool.execute(usedRepoScheduler);
        UserScheduler userScheduler = UserScheduler.builder().userService(userService).build();
        pool.execute(userScheduler);
    }
}
