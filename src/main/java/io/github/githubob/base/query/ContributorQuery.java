package io.github.githubob.base.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContributorQuery {
    private long repoId;
    private long contributorId;
}
