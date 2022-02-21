package io.github.githubob.base.constants;

public class RedisKey {
    //密钥激活队列
    public static final String SECRETS_ALIVE_QUEUE="SECRETS_ALIVE_QUEUE";
    //密钥失效队列
    public static final String SECRETS_NOALIVE_QUEUE="SECRETS_NOALIVE_QUEUE";
    //获取协作者信息等待队列
    public static final String  WAIT_GET_CONTRIBUTOR_QUERE="WAIT_GET_CONTRIBUTOR_QUERE";
    //获取用户仓库信息等待队列
    public static final String  WAIT_GET_REPOS_QUERE="WAIT_GET_REPOS_QUERE";
    //获取协作者用户信息队列
    public static final String  WAIT_USER_SET="WAIT_USER_SET";

}
