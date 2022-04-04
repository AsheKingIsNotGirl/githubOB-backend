package io.github.githubob.spider.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.githubob.spider.entity.Param;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

public abstract class AbstrastSpiderService {
    protected RestTemplate restTemplate;

    public AbstrastSpiderService() {
        restTemplate=new RestTemplateBuilder().basicAuthentication("3177b31e134ddcbd9a48","5e17f09e1fed5801710c2e32445c7fc24c74c021").build();
    }

    protected String arraynodeToString(ArrayNode node){
        StringBuilder array=new StringBuilder();
        for (int i =0;i<node.size();i++){
            array.append(node.get(i).asText()).append(",");
        }
        if (array.length()>1){
            array.substring(0,array.length()-1);
        }
        return array.toString();
    }

    public abstract void saveData(Param param) throws JsonProcessingException;
}
