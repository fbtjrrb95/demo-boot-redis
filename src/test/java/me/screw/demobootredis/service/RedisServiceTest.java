package me.screw.demobootredis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    public void initialize() {
        while(redisTemplate.opsForList().size("token") >0) {
            redisTemplate.opsForList().rightPop("token");
        }
    }
    @Test
    void initializeToken() {
        redisService.initializeToken(10);
        assertEquals(redisTemplate.opsForList().size("token"), 10l);
    }

    @Test
    void getToken() {
        redisTemplate.opsForList().rightPush("token","coupon_uuid");
        assertEquals(redisService.getToken(),"coupon_uuid");
    }

    @Test
    void getNullToken() {
        assertEquals(redisService.getToken(), null);
    }
}