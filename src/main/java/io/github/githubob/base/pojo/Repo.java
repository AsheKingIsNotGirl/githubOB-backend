package io.github.githubob.base.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Repo {
    private long repoSn;
    private long repoId;
    private String repoName;
    private String repoFullName;
    private long userId;
    private String repoDescription;
    private int isFork;
    private int repoStarCount;
    private int repoForkCount;
    private int repoWatchCount;
    private String repoLicense;
    private String repoTopics;
    private String repoLanguages;
    private String etag;
    private Date createTime;
    private String contributorsUrl;
    private String ownerLogin;
}
