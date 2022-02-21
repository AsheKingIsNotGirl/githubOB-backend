package io.github.githubob.spider.scheduler;

import io.github.githubob.base.constants.RedisKey;
import io.github.githubob.spider.job.ContributorRunable;
import io.github.githubob.spider.job.UsedRepoRunable;
import io.github.githubob.spider.service.impl.RepoServiceImpl;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Builder
public class UsedRepoScheduler implements Runnable{
    private RepoServiceImpl repoService;
    private RestTemplate restTemplate;
    @Override
    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Jedis jedisTemplate=new Jedis("localhost",6379);
        while (true){
            try {
                String url = jedisTemplate.lpop(RedisKey.WAIT_GET_REPOS_QUERE);
                if (StringUtils.isNotBlank(url)){
                    UsedRepoRunable runable = UsedRepoRunable.builder().repoService(repoService).restTemplate(restTemplate).jedisTemplate(jedisTemplate).url(url).build();
                    pool.execute(runable);
                }else{
                    Thread.sleep(5*1000);
                }
            }catch (Exception e){
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
}
