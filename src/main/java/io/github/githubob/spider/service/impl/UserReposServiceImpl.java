package io.github.githubob.spider.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.githubob.base.config.RedisHelper;
import io.github.githubob.base.constants.RedisKey;
import io.github.githubob.base.pojo.Repo;
import io.github.githubob.base.query.RepoQuery;
import io.github.githubob.spider.dao.ContributorDao;
import io.github.githubob.spider.dao.RepoDao;
import io.github.githubob.spider.entity.Param;
import io.github.githubob.spider.entity.RepoInfo;
import io.github.githubob.spider.service.AbstrastSpiderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;

@Service("repoService")
public class UserReposServiceImpl extends AbstrastSpiderService {
    @Resource
    private RepoDao repoDao;
    @Resource
    private ContributorDao contributorDao;
    private Logger logger= LoggerFactory.getLogger(UserReposServiceImpl.class);

    /**
     * 请求链接得到仓库信息存入数据库
     * @param param
     * @throws JsonProcessingException
     */
    @Override
    public void saveData(Param param) throws JsonProcessingException {
        Jedis jedisTemplate = RedisHelper.getRedisConnection();
        //查询是否已经存在数据
        RepoQuery query=RepoQuery.builder().repoFullName(param.getUser()+"/"+param.getRepo()).build();
        List<Repo> repoList = repoDao.queryRepo(query);
        if (repoList.size()>0){
            logger.warn("{}/{}仓库信息数据库已存在",param.getUser(),param.getRepo());
            return;
        }
        String url=param.getUrl();
        logger.info("请求地址：{}",url);
        ResponseEntity<String> body = restTemplate.getForEntity(url, String.class);
        if (body.getStatusCode().isError()){
            logger.error("response code:{}",body.getStatusCodeValue());
            return;
        }
        logger.debug("请求内容为：{}",body.getBody());
        //装载数据
        Repo repo=parse(body.getBody());
        repo.setEtag(body.getHeaders().get("Etag").get(0));
        repoDao.addRepo(repo);
        //将该仓库信息加入请求协作者队列中
        RepoInfo repoInfo=new RepoInfo();
        repoInfo.setUrl(repo.getContributorsUrl());
        repoInfo.setUser(param.getUser());
        repoInfo.setRepo(param.getRepo());
        jedisTemplate.lpush(RedisKey.WAIT_GET_CONTRIBUTOR_QUERE,new ObjectMapper().writeValueAsString(repoInfo));
        jedisTemplate.close();
    }

    /**
     * 解析仓库信息字符串存入数据库
     * @param json
     * @throws JsonProcessingException
     */
    public void saveData(String json) throws JsonProcessingException {
        Jedis jedisTemplate = RedisHelper.getRedisConnection();
        Repo repo = parse(json);
        RepoQuery query = RepoQuery.builder().repoFullName(repo.getRepoFullName()).build();
        List<Repo> repoList = repoDao.queryRepo(query);
        if (repoList.size()>0){
            logger.warn("{}仓库信息数据库已存在",repo.getRepoFullName());
            return;
        }
        repoDao.addRepo(repo);
        //将该仓库信息加入请求协作者队列中
        RepoInfo repoInfo=new RepoInfo();
        repoInfo.setUrl(repo.getContributorsUrl());
        repoInfo.setUser(repo.getOwnerLogin());
        repoInfo.setRepo(repo.getRepoName());
        jedisTemplate.lpush(RedisKey.WAIT_GET_CONTRIBUTOR_QUERE,new ObjectMapper().writeValueAsString(repoInfo));
        jedisTemplate.close();
    }

    private Repo parse(String json) throws JsonProcessingException {
        Repo repo=new Repo();
        try {
            ObjectNode repoNode = new ObjectMapper().readValue(json, ObjectNode.class);
            repo.setRepoId(repoNode.get("id").asLong());
            repo.setRepoName(repoNode.get("name").asText());
            repo.setRepoFullName(repoNode.get("full_name").asText());
            repo.setUserId(repoNode.get("owner").get("id").asLong());
            repo.setOwnerLogin(repoNode.get("owner").get("login").asText());
            repo.setRepoDescription(repoNode.get("description").asText());
            repo.setIsFork(repoNode.get("fork").asBoolean()?1:0);
            repo.setRepoStarCount(repoNode.get("stargazers_count").asInt());
//            repo.setRepoWatchCount(repoNode.get("subscribers_count").asInt());
            repo.setRepoForkCount(repoNode.get("forks_count").asInt());
            repo.setRepoLicense(repoNode.get("license").asText());
            repo.setRepoTopics(arraynodeToString((ArrayNode)repoNode.get("topics")));
            repo.setContributorsUrl(repoNode.get("contributors_url").asText());
        }catch (Exception e){
            logger.debug("解析json字符串报错：{}",json);
            throw e;
        }
        return repo;
    }
}
