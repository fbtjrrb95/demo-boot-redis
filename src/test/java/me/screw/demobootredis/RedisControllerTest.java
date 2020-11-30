package me.screw.demobootredis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableWebMvc
public class RedisControllerTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testDataHandling() throws Exception {
        String key = "key:springboot";
        redisTemplate.opsForValue().set(key, "Hello");
        String value = (String)redisTemplate.opsForValue().get(key);
        Assert.assertEquals("Hello", value);
    }
}