package io.github.githubob.spider.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Param {
    private String user;
    private String repo;
}
