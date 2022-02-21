package io.github.githubob.spider.dao;

import io.github.githubob.base.pojo.Contributor;
import io.github.githubob.base.query.ContributorQuery;

import java.util.List;

public interface ContributorDao {
    public int addContributor(Contributor contributor);
    public int deleteContributor(ContributorQuery query);
}
