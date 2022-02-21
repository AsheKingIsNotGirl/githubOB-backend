package io.github.githubob.base.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserQuery {
    private long userId;
    private String userLoginName;
    private String userName;
}
