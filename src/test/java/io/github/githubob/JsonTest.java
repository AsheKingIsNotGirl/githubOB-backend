package io.github.githubob;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GithubObApplication.class)
public class JsonTest {
    @Test
    public void test1(){
        String json="{\n" +
                "    \"login\": \"Snailclimb\",\n" +
                "    \"id\": 29880145,\n" +
                "    \"node_id\": \"MDQ6VXNlcjI5ODgwMTQ1\",\n" +
                "    \"avatar_url\": \"https://avatars.githubusercontent.com/u/29880145?v=4\",\n" +
                "    \"gravatar_id\": \"\",\n" +
                "    \"url\": \"https://api.github.com/users/Snailclimb\",\n" +
                "    \"html_url\": \"https://github.com/Snailclimb\",\n" +
                "    \"followers_url\": \"https://api.github.com/users/Snailclimb/followers\",\n" +
                "    \"following_url\": \"https://api.github.com/users/Snailclimb/following{/other_user}\",\n" +
                "    \"gists_url\": \"https://api.github.com/users/Snailclimb/gists{/gist_id}\",\n" +
                "    \"starred_url\": \"https://api.github.com/users/Snailclimb/starred{/owner}{/repo}\",\n" +
                "    \"subscriptions_url\": \"https://api.github.com/users/Snailclimb/subscriptions\",\n" +
                "    \"organizations_url\": \"https://api.github.com/users/Snailclimb/orgs\",\n" +
                "    \"repos_url\": \"https://api.github.com/users/Snailclimb/repos\",\n" +
                "    \"events_url\": \"https://api.github.com/users/Snailclimb/events{/privacy}\",\n" +
                "    \"received_events_url\": \"https://api.github.com/users/Snailclimb/received_events\",\n" +
                "    \"type\": \"User\",\n" +
                "    \"site_admin\": false,\n" +
                "    \"name\": \"Guide\",\n" +
                "    \"company\": null,\n" +
                "    \"blog\": \"https://javaguide.cn/\",\n" +
                "    \"location\": \"Wuhan, Hubei\",\n" +
                "    \"email\": null,\n" +
                "    \"hireable\": null,\n" +
                "    \"bio\": \"一只向上爬的蜗牛\uD83D\uDC0C。 \\r\\n\",\n" +
                "    \"twitter_username\": null,\n" +
                "    \"public_repos\": 22,\n" +
                "    \"public_gists\": 0,\n" +
                "    \"followers\": 8491,\n" +
                "    \"following\": 109,\n" +
                "    \"created_at\": \"2017-07-04T03:55:47Z\",\n" +
                "    \"updated_at\": \"2022-03-22T02:17:46Z\"\n" +
                "}";

    }
}
