package io.github.githubob;

import io.github.githubob.base.config.RedisHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.List;

@SpringBootTest
public class JedisTest {

    @Test
    public void test1(){
        Jedis jedis=new Jedis("localhost",6379);
        jedis.set("test","test");
        System.out.println(jedis.get("test"));
    }
    @Test
    public void test2(){
        System.out.println(RedisHelper.getRedisConnection().set("test","test"));
        System.out.println(RedisHelper.getRedisConnection().get("test"));
    }
    @Test
    public void test3(){
        Jedis jedis = RedisHelper.getRedisConnection();
        List<String> testList = jedis.blpop(500,"testList");
        System.out.println(testList);
    }
}
