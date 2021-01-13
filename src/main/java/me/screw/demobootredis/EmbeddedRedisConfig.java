package me.screw.demobootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class EmbeddedRedisConfig {

    @Autowired
    RedisProperties redisProperties;

    private RedisServer redisServer;
    public EmbeddedRedisConfig(RedisProperties redisProperties) {
        this.redisServer = RedisServer.builder()
                .port(redisProperties.getRedisPort())
                .setting("requirepass screw")
                .setting("maxheap 128mb")
                .build();
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}
