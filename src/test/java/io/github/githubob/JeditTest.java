package io.github.githubob;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

@SpringBootTest
public class JeditTest {
    @Autowired
    private Jedis redisTemplate;

    @Test
    public void test1(){
        Jedis jedis=new Jedis("localhost",6379);
        jedis.set("test","test");
        System.out.println(jedis.get("test"));
    }
    @Test
    public void test2(){
        System.out.println(redisTemplate.get("test"));
    }
}
