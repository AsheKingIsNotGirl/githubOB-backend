package io.github.githubob.spider.scheduler;

import io.github.githubob.base.constants.RedisKey;
import io.github.githubob.spider.dao.ContributorDao;
import io.github.githubob.spider.job.ContributorRunable;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Builder
public class ContributorScheduler implements Runnable{
    private ContributorDao contributorDao;
    private RestTemplate restTemplate;
    @Override
    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Jedis jedisTemplate=new Jedis("localhost",6379);
        while (true){
            try {
                String info = jedisTemplate.lpop(RedisKey.WAIT_GET_CONTRIBUTOR_QUERE);
                if (StringUtils.isNotBlank(info)){
                    ContributorRunable runable = ContributorRunable.builder().contributorDao(contributorDao).jedisTemplate(jedisTemplate).restTemplate(restTemplate).info(info).build();
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
