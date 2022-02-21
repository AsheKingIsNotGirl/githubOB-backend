package io.github.githubob.spider.dao;

import io.github.githubob.base.pojo.Repo;
import io.github.githubob.base.query.RepoQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface RepoDao {
    public int addRepo(Repo repo);
    public List<Repo> queryRepo(RepoQuery query);
}
