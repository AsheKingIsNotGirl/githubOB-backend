package io.github.githubob.spider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.githubob.base.pojo.Repo;
import io.github.githubob.spider.api.GithubAPI;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
public class RestTest {

    @Test
    public void baseTest() throws JsonProcessingException {
        RestTemplateBuilder builder=new RestTemplateBuilder();
        RestTemplate template = builder.basicAuthentication("3177b31e134ddcbd9a48", "5e17f09e1fed5801710c2e32445c7fc24c74c021").build();
        ResponseEntity<String> body = template.getForEntity(GithubAPI.getRepoInfoUrl("Snailclimb","JavaGuide"), String.class);
        System.out.println("response code:"+body.getStatusCodeValue());
        System.out.println("body:"+body.getBody());
        ObjectNode repoNode = new ObjectMapper().readValue(body.getBody(), ObjectNode.class);
        System.out.println("id:"+repoNode.get("id").asLong());
        System.out.println("name:"+repoNode.get("name").asText());
        System.out.println("full_name:"+repoNode.get("full_name").asText());
        System.out.println("user_id:"+repoNode.get("owner").get("id").asLong());
        System.out.println("description:"+repoNode.get("description").asText());
        System.out.println("fork:"+repoNode.get("fork").asBoolean());
        System.out.println("stargazers_count:"+repoNode.get("stargazers_count").asInt());
        System.out.println("subscribers_count:"+repoNode.get("subscribers_count").asInt());
        System.out.println("forks_count:"+repoNode.get("forks_count").asInt());
        System.out.println("license:"+repoNode.get("license").asText());
        System.out.println("topics:"+arraynodeToString((ArrayNode)repoNode.get("topics")));
        System.out.println("Etag:"+body.getHeaders().get("Etag").get(0));
    }
    public String arraynodeToString(ArrayNode node){
        StringBuilder array=new StringBuilder();
        for (int i =0;i<node.size();i++){
            array.append(node.get(i).asText()).append(",");
        }
        array.substring(0,array.length()-1);
        return array.toString();
    }
}
