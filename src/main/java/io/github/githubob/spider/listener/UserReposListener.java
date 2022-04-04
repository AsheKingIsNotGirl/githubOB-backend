package io.github.githubob.spider.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.githubob.base.config.RedisHelper;
import io.github.githubob.base.config.RestPool;
import io.github.githubob.base.constants.RedisKey;
import io.github.githubob.spider.entity.Param;
import io.github.githubob.spider.entity.RepoInfo;
import io.github.githubob.spider.service.impl.UserReposServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserReposListener implements Listener{
    @Resource
    private UserReposServiceImpl repoService;
    @Resource
    private RestPool restPool;
    @Override
    public void run() {
        Jedis jedis = RedisHelper.getRedisConnection();
        while (!Thread.interrupted()){
            try {
                List<String> list = jedis.blpop(5, RedisKey.WAIT_USER_REPOS_QUERE);
                if (list==null){
                    log.info("{}队列为空",RedisKey.WAIT_USER_REPOS_QUERE);
                    continue;
                }
                String url = list.get(1);
                log.info("{}队列中取出链接为{}",RedisKey.WAIT_USER_REPOS_QUERE,url);
                ResponseEntity<String> entity = restPool.request(url);
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode arrayNode = mapper.readValue(entity.getBody(), ArrayNode.class);
                log.info("{}url包含{}个仓库",url,arrayNode.size());
                for (int i=0;i< arrayNode.size();i++){
                    log.info("{}url第{}个仓库信息进行保存",url,i);
                    repoService.saveData(arrayNode.get(i).toString());
                }
//                RepoInfo repoInfo = new ObjectMapper().readValue(json, RepoInfo.class);
//                Param param = Param.builder().user(repoInfo.getUser()).repo(repoInfo.getRepo()).url(repoInfo.getUrl()).build();
//                repoService.saveData(param);
            }catch (Exception e){
                log.error("执行错误",e);
            }
        }
    }
}
