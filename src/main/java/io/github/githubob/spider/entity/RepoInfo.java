package io.github.githubob.spider.entity;

import lombok.Data;

@Data
public class RepoInfo {
    private String user;
    private String repo;
    private String url;
}
