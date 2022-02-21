package io.github.githubob.base.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private long userId;
    private String userLoginName;
    private String userName;
    private String userAvatarUrl;
    private String userCompany;
    private String userBlog;
    private String userLocation;
    private String userEmail;
    private String userBio;
    private Date createTime;
}
