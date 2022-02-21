package io.github.githubob.spider.job;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.githubob.spider.entity.Param;
import io.github.githubob.spider.service.impl.RepoServiceImpl;
import io.github.githubob.spider.service.impl.UserServiceImpl;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

@Builder
public class UserRunable implements Runnable{
    private UserServiceImpl userService;
    private String user;
    @Override
    public void run() {
        Logger logger= LoggerFactory.getLogger(UserRunable.class);
        try {
            userService.saveData(Param.builder().user(user).build());
        }catch (Exception e){
            logger.error("获取用户信息线程执行异常",e);
        }
    }
}
