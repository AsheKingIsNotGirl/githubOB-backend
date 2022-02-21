package io.github.githubob.spider.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.githubob.base.constants.RedisKey;
import io.github.githubob.base.pojo.Contributor;
import io.github.githubob.base.pojo.Repo;
import io.github.githubob.base.query.RepoQuery;
import io.github.githubob.spider.api.GithubAPI;
import io.github.githubob.spider.dao.ContributorDao;
import io.github.githubob.spider.dao.RepoDao;
import io.github.githubob.spider.entity.Param;
import io.github.githubob.spider.service.AbstrastSpiderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("repoService")
public class RepoServiceImpl extends AbstrastSpiderService {
    @Resource
    private RepoDao repoDao;
    @Resource
    private ContributorDao contributorDao;
    private Logger logger= LoggerFactory.getLogger(RepoServiceImpl.class);
    @Override
    public void saveData(Param param) throws JsonProcessingException {
        //查询是否已经存在数据
        RepoQuery query=RepoQuery.builder().repoFullName(param.getUser()+"/"+param.getRepo()).build();
        List<Repo> repoList = repoDao.queryRepo(query);
        if (repoList.size()>0){
            logger.warn("{}/{}仓库信息数据库已存在",param.getUser(),param.getRepo());
            return;
        }
        String url=GithubAPI.getRepoInfoUrl(param.getUser(),param.getRepo());
        logger.info("请求地址：{}",url);
        ResponseEntity<String> body = restTemplate.getForEntity(url, String.class);
        if (body.getStatusCode().isError()){
            logger.error("response code:{}",body.getStatusCodeValue());
            return;
        }
        logger.debug("请求内容为：{}",body.getBody());
        ObjectNode repoNode = new ObjectMapper().readValue(body.getBody(), ObjectNode.class);
        //将该仓库信息加入请求协作者队列中
        jedisTemplate.lpush(RedisKey.WAIT_GET_CONTRIBUTOR_QUERE,repoNode.get("id").asLong()+";"+repoNode.get("contributors_url").asText());
        //装载数据
        Repo repo=new Repo();
        repo.setRepoId(repoNode.get("id").asLong());
        repo.setRepoName(repoNode.get("name").asText());
        repo.setRepoFullName(repoNode.get("full_name").asText());
        repo.setUserId(repoNode.get("owner").get("id").asLong());
        repo.setRepoDescription(repoNode.get("description").asText());
        repo.setIsFork(repoNode.get("fork").asBoolean()?1:0);
        repo.setRepoStarCount(repoNode.get("stargazers_count").asInt());
        repo.setRepoWatchCount(repoNode.get("subscribers_count").asInt());
        repo.setRepoForkCount(repoNode.get("forks_count").asInt());
        repo.setRepoLicense(repoNode.get("license").asText());
        repo.setRepoTopics(arraynodeToString((ArrayNode)repoNode.get("topics")));
        repo.setEtag(body.getHeaders().get("Etag").get(0));
        repoDao.addRepo(repo);
    }
}
