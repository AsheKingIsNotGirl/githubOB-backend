package io.github.githubob.spider.dao;

import io.github.githubob.GithubObApplication;
import io.github.githubob.base.pojo.Repo;
import io.github.githubob.base.query.RepoQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = GithubObApplication.class)
public class RepoDaoTest {
    @Resource
    private RepoDao repoDao;
    @Test
    public void add(){
        repoDao.addRepo(new Repo());
    }
    @Test
    public void query(){
        RepoQuery query = RepoQuery.builder().repoId(132464395).build();
        System.out.println(repoDao.queryRepo(query));
    }
}
