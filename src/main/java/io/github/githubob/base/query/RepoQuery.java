package io.github.githubob.base.query;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RepoQuery {
    private long repoId;
    private String repoName;
    private String repoFullName;
    private long userId;
    private String repoTopics;
    private String repoLanguages;
}
