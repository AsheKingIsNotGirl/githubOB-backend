package io.github.githubob.spider.dao;

import io.github.githubob.base.pojo.OauthClient;

import java.util.List;

public interface OauthClientDao {
    public List<OauthClient> getAllOauthClient();
}
