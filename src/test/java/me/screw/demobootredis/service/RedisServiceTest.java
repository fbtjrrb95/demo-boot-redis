package me.screw.demobootredis.service;

import me.screw.demobootredis.config.EmbeddedRedisConfig;
import me.screw.demobootredis.config.RedisConfiguration;
import me.screw.demobootredis.config.RedisProperties;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
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