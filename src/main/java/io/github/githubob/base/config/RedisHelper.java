package io.github.githubob.base.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisHelper {
    private static JedisPool pool;

    private RedisHelper(){
    }

    private static void config(){
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxIdle(100);
        config.setMaxTotal(100);
        config.setTestWhileIdle(true);
        pool=new JedisPool(config,"localhost",6379);
    }

    private static JedisPool getPool(){
        if (pool==null){
            synchronized (RedisHelper.class){
                if (pool==null){
                    config();
                }
            }
        }
        return pool;
    }

    public static Jedis getRedisConnection(){
        return getPool().getResource();
    }

}
