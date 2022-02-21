package io.github.githubob.spider.dao;

import io.github.githubob.GithubObApplication;
import io.github.githubob.base.pojo.User;
import io.github.githubob.base.query.UserQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = GithubObApplication.class)
public class UserDaoTest {
    @Resource
    private UserDao userDao;
    @Test
    public void add(){
        User user=new User();
        user.setUserId(1);
        userDao.addUser(user);
    }
    @Test
    public void queryById(){
        System.out.println(userDao.queryUser(UserQuery.builder().userLoginName("aaa").build()));
    }
}
