package io.github.githubob.spider.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.githubob.base.config.RedisHelper;
import io.github.githubob.base.config.RestPool;
import io.github.githubob.base.constants.RedisKey;
import io.github.githubob.spider.entity.Param;
import io.github.githubob.spider.entity.RepoInfo;
import io.github.githubob.spider.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class ContributorListener implements Listener{
    @Resource
    private RestPool restPool;
    @Override
    public void run() {
        Jedis jedis = RedisHelper.getRedisConnection();
        while (!Thread.interrupted()){
            try {
                List<String> list = jedis.blpop(5, RedisKey.WAIT_GET_CONTRIBUTOR_QUERE);
                if (list==null){
                    log.info("{}队列为空",RedisKey.WAIT_GET_CONTRIBUTOR_QUERE);
                    continue;
                }
                String json = list.get(1);
                log.info("{}队列中取出对象为{}",RedisKey.WAIT_GET_CONTRIBUTOR_QUERE,json);
                RepoInfo repoInfo = new ObjectMapper().readValue(json, RepoInfo.class);
                ResponseEntity<String> entity = restPool.request(repoInfo.getUrl());
                ArrayNode arrayNode = new ObjectMapper().readValue(entity.getBody(), ArrayNode.class);
                for (int i=0;i<arrayNode.size();i++){
                    UserInfo userInfo=new UserInfo();
                    userInfo.setUser(arrayNode.get(i).get("login").asText());
                    userInfo.setUrl(arrayNode.get(i).get("url").asText());
                    String value = new ObjectMapper().writeValueAsString(userInfo);
                    jedis.lpush(RedisKey.WAIT_USER_SET,value);
                    log.info("{}队列插入数据{}",RedisKey.WAIT_USER_SET,value);
                }

            }catch (Exception e){
                log.error("执行错误",e);
            }
        }
    }
}
