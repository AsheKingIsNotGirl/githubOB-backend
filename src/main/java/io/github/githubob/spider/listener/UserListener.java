package io.github.githubob.spider.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.githubob.base.config.RedisHelper;
import io.github.githubob.base.constants.RedisKey;
import io.github.githubob.spider.entity.Param;
import io.github.githubob.spider.entity.UserInfo;
import io.github.githubob.spider.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserListener implements Listener{

    @Resource
    private UserServiceImpl userService;

    @Override
    public void run() {
        Jedis jedis = RedisHelper.getRedisConnection();
        while (!Thread.interrupted()){
            try {
                List<String> list = jedis.blpop(5, RedisKey.WAIT_USER_SET);
                if (list==null){
                    log.info("{}队列为空",RedisKey.WAIT_USER_SET);
                    continue;
                }
                String json = list.get(1);
                log.info("{}队列中取出对象为{}",RedisKey.WAIT_USER_SET,json);
                UserInfo userInfo = new ObjectMapper().readValue(json, UserInfo.class);
                Param param = Param.builder().user(userInfo.getUser()).url(userInfo.getUrl()).build();
                userService.saveData(param);
            }catch (Exception e){
                log.error("执行错误",e);
            }
        }
    }
}
