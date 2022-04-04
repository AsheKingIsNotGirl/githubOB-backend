package io.github.githubob.base.config;

import io.github.githubob.base.pojo.OauthClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.Semaphore;
@Slf4j
public class RestPool {
    Map<String,RestTemplate> templateMap;
    Queue<String> activeQueue;
    List<String> nonActiveList;
    Semaphore semaphore;

    public RestPool(List<OauthClient> oauthClientList) {
        activeQueue=new ArrayDeque<>();
        nonActiveList=new ArrayList<>();
        templateMap=new HashMap<>();
        for (OauthClient client:oauthClientList){
            RestTemplate template = new RestTemplateBuilder().basicAuthentication(client.getClientId(), client.getClientSecrets()).build();
            templateMap.put(client.getClientId(),template);
            activeQueue.add(client.getClientId());
        }
        semaphore=new Semaphore(oauthClientList.size());
    }

    public ResponseEntity<String> request(String url) throws InterruptedException {
        String clientid = pollTemplate();
        ResponseEntity<String> entity;
        try {
            entity = templateMap.get(clientid).getForEntity(url, String.class);
            while (entity.getStatusCodeValue()==403){
                addNonActiveList(clientid);
                clientid = pollTemplate();
                entity = templateMap.get(clientid).getForEntity(url, String.class);
            }
            pushTemplate(clientid);
            List<String> maxLimitList = entity.getHeaders().get("X-RateLimit-Limit");
            List<String> limitList = entity.getHeaders().get("X-RateLimit-Remaining");
            String maxLimit=maxLimitList.size()>0?null: maxLimitList.get(0);
            String limit=limitList.size()>0?null: limitList.get(0);
            if (maxLimit!=null&&limit!=null){
                log.info("密钥{}使用次数:{}/{}",clientid,limit,maxLimit);
            }
            log.info("{}请求成功",url);
        }catch (Exception e){
            log.error("{}请求错误",url);
            throw e;
        }
        return entity;
    }

    public String pollTemplate() throws InterruptedException {
        String clientid = activeQueue.poll();
        semaphore.acquire();
        return clientid;
    }

    public void pushTemplate(String clientid){
        activeQueue.add(clientid);
        semaphore.release();
    }

    public void addNonActiveList(String clientid){
        nonActiveList.add(clientid);
    }
    public void clearNonActiveList(){
        log.info("开始重置失效的密钥");
        for (String clientid:nonActiveList){
            activeQueue.add(clientid);
        }
    }
}
