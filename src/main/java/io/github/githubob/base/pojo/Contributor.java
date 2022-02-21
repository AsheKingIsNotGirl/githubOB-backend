package io.github.githubob.base.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Contributor {
    private long contributorSn;
    private long repoId;
    private long contributorId;
    private Date createTime;
}
