package io.github.githubob.spider.api;

public class GithubAPI {
    private static String BASE_URL="https://api.github.com";

    public static String getUserInfoUrl(String user){
        return new StringBuilder(BASE_URL).append("/users/").append(user).toString();
    }
    public static String getRepoInfoUrl(String user,String repo){
        return new StringBuilder(BASE_URL)
                .append("/repos/")
                .append(user)
                .append("/")
                .append(repo)
                .toString();
    }
    public static String getContributorsInfoUrl(String user,String repo){
        return new StringBuilder(BASE_URL)
                .append("/repos/")
                .append(user)
                .append("/")
                .append(repo)
                .append("/")
                .append("contributors")
                .toString();
    }
}
