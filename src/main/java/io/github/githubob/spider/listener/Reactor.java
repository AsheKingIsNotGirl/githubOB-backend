package io.github.githubob.spider.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Reactor implements CommandLineRunner {
    @Resource
    private Map<String,Listener> listenerMap;

    @Override
    public void run(String... args) throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(listenerMap.size());
        for(String key:listenerMap.keySet()){
            threadPool.execute(listenerMap.get(key));
        }
    }
}
