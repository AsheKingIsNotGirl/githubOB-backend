package io.github.githubob.spider.dao;

import io.github.githubob.base.pojo.User;
import io.github.githubob.base.query.UserQuery;

import java.util.List;

public interface UserDao {
    public int addUser(User user);
    public List<User> queryUser(UserQuery query);
}
