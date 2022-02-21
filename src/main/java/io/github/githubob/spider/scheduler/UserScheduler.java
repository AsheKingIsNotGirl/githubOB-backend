package io.github.githubob.spider.scheduler;

import io.github.githubob.base.constants.RedisKey;
import io.github.githubob.spider.job.UsedRepoRunable;
import io.github.githubob.spider.job.UserRunable;
import io.github.githubob.spider.service.impl.UserServiceImpl;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Builder
public class UserScheduler implements Runnable{
    private UserServiceImpl userService;
    @Override
    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Jedis jedisTemplate=new Jedis("localhost",6379);
        while (true){
            try {
                String user = jedisTemplate.spop(RedisKey.WAIT_USER_SET);
                if (StringUtils.isNotBlank(user)){
                    UserRunable runable = UserRunable.builder().userService(userService).user(user).build();
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
