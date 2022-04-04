package io.github.githubob.base.config;

import io.github.githubob.base.pojo.OauthClient;
import io.github.githubob.spider.dao.OauthClientDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class RestPoolConfig {
    @Resource
    private OauthClientDao oauthClientDao;

    @Bean(value = "restPool")
    public RestPool configRestPool(){
        final List<OauthClient> clientList = oauthClientDao.getAllOauthClient();
        return new RestPool(clientList);
    }
}
