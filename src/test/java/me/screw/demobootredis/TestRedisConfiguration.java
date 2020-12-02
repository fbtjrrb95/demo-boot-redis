package me.screw.demobootredis;

import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
public class TestRedisConfiguration {

    private RedisServer redisServer;
    public TestRedisConfiguration(RedisProperties redisProperties) {
        this.redisServer = RedisServer.builder().port(redisProperties.getRedisPort()).setting("maxheap 128mb").build();
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
