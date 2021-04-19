package me.screw.demobootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void initializeToken(int totalCnt){
        UUID uuid = null;
        for(int i=0;i<totalCnt;i++) {
            uuid = randomUUID();
            redisTemplate.opsForList().leftPush("token", uuid.toString());
        }
    }

    public String getToken(){
        long totalSize = redisTemplate.opsForList().size("token");
        if(totalSize > 0l) {
            return (String) redisTemplate.opsForList().rightPop("token");
        }
        return null;
    }
}
