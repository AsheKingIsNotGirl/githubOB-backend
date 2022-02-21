package io.github.githubob.spider.job;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.githubob.spider.entity.Param;
import io.github.githubob.spider.service.impl.RepoServiceImpl;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
@Builder
public class UsedRepoRunable implements Runnable{
    private RepoServiceImpl repoService;
    private String url;
    private Jedis jedisTemplate;
    private RestTemplate restTemplate;
    @Override
    public void run() {
        Logger logger= LoggerFactory.getLogger(UsedRepoRunable.class);
        try {
            ResponseEntity<String> body = restTemplate.getForEntity(url, String.class);
            if (body.getStatusCode().isError()){
                logger.error("response code:{}",body.getStatusCodeValue());
                return;
            }
            logger.debug("请求内容为：{}",body.getBody());
            ArrayNode arrayNode = new ObjectMapper().readValue(body.getBody(), ArrayNode.class);
            for (int i=0;i<arrayNode.size();i++){
                JsonNode node = arrayNode.get(i);
                String repo=node.get("name").asText();
                String user=node.get("owner").get("login").asText();
                repoService.saveData(Param.builder().user(user).repo(repo).build());
            }
        }catch (Exception e){
            logger.error("获取用户仓库线程执行异常",e);
        }
    }
}
