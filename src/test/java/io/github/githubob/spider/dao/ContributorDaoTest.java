package io.github.githubob.spider.dao;

import io.github.githubob.GithubObApplication;
import io.github.githubob.base.pojo.Contributor;
import io.github.githubob.base.query.ContributorQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = GithubObApplication.class)
public class ContributorDaoTest {
    @Resource
    private ContributorDao contributorDao;

    @Test
    public void add(){
        Contributor contributor=new Contributor();
        contributor.setRepoId(1);
        contributor.setContributorId(1);
        contributorDao.addContributor(contributor);
    }
    @Test
    public void delete(){
        contributorDao.deleteContributor(ContributorQuery.builder().repoId(1).build());
    }
}
