package io.github.githubob.spider.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.githubob.base.constants.RedisKey;
import io.github.githubob.base.pojo.User;
import io.github.githubob.base.query.UserQuery;
import io.github.githubob.spider.api.GithubAPI;
import io.github.githubob.spider.dao.UserDao;
import io.github.githubob.spider.entity.Param;
import io.github.githubob.spider.service.AbstrastSpiderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl extends AbstrastSpiderService {
    @Resource
    private UserDao userDao;
    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void saveData(Param param) throws JsonProcessingException {
        UserQuery query=UserQuery.builder().userLoginName(param.getUser()).build();
        List<User> userList = userDao.queryUser(query);
        if (userList.size()>0){
            logger.warn("{}用户信息数据库已存在",param.getUser());
            return;
        }
        String url=GithubAPI.getUserInfoUrl(param.getUser());
        logger.info("请求地址：{}",url);
        ResponseEntity<String> body = restTemplate.getForEntity(url, String.class);
        if (body.getStatusCode().isError()){
            logger.error("response code:{}",body.getStatusCodeValue());
            return;
        }
        logger.debug("请求内容为：{}",body.getBody());
        ObjectNode userNode = new ObjectMapper().readValue(body.getBody(), ObjectNode.class);
        //将请求仓库url加入队列中
        jedisTemplate.lpush(RedisKey.WAIT_GET_REPOS_QUERE,userNode.get("repos_url").asText());
        //装载数据
        User user=new User();
        user.setUserId(userNode.get("id").asLong());
        user.setUserLoginName(userNode.get("login").asText());
        user.setUserName(userNode.get("name").asText());
        user.setUserAvatarUrl(userNode.get("avatar_url").asText());
        user.setUserCompany(userNode.get("company").asText());
        user.setUserBlog(userNode.get("blog").asText());
        user.setUserLocation(userNode.get("location").asText());
        user.setUserEmail(userNode.get("email").asText());
        userDao.addUser(user);
    }
}
