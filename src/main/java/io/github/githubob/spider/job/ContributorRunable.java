package io.github.githubob.spider.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.githubob.base.constants.RedisKey;
import io.github.githubob.base.pojo.Contributor;
import io.github.githubob.spider.dao.ContributorDao;
import io.github.githubob.spider.service.impl.RepoServiceImpl;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

@Builder
public class ContributorRunable implements Runnable{
    private ContributorDao contributorDao;
    private String info;
    private Jedis jedisTemplate;
    private RestTemplate restTemplate;

    @Override
    public void run() {
        Logger logger= LoggerFactory.getLogger(ContributorRunable.class);
        try {
            String[] split = info.split(";");
            long repoid=Long.valueOf(split[0]);
            String url=split[1];

            ResponseEntity<String> body = restTemplate.getForEntity(url, String.class);
            if (body.getStatusCode().isError()){
                logger.error("response code:{}",body.getStatusCodeValue());
                return;
            }
            logger.debug("请求内容为：{}",body.getBody());
            ArrayNode arrayNode = new ObjectMapper().readValue(body.getBody(), ArrayNode.class);
            for (int i=0;i<arrayNode.size();i++){
                JsonNode node = arrayNode.get(i);
                Contributor contributor=new Contributor();
                contributor.setRepoId(repoid);
                contributor.setContributorId(node.get("id").asLong());
                contributorDao.addContributor(contributor);
                jedisTemplate.sadd(RedisKey.WAIT_USER_SET,node.get("login").asText());
            }
        } catch (Exception e) {
            logger.error("获取协作者线程执行异常",e);
        }
    }
}
