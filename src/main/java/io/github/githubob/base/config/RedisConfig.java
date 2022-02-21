package io.github.githubob.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
@Configuration
public class RedisConfig {
    @Bean(name = "jedisTemplate")
    public Jedis getRedis(){
        Jedis jedis=new Jedis("localhost",6379);
        return jedis;
    }
}
