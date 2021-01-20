package me.screw.demobootredis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RedisControllerTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getTokenTest() throws Exception {
        mockMvc.perform(get("/redis/token"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/post"))
                ;
    }

    @Test
    public void setTokenTest() throws Exception {
        mockMvc.perform(get("/token/init"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/form"))
        ;
    }

    @Test
    public void getCouponsTest() throws Exception {

        redisTemplate.opsForList().leftPush("token", "1234");
        Coupons coupons = new Coupons();
        coupons.setUsername("seokkyu");
        coupons.setToken((String)redisTemplate.opsForList().rightPop("token"));
        redisTemplate.opsForList().leftPush("coupons",coupons.toString());

        mockMvc.perform(get("/redis/coupons")
                    .param("username","seokkyu")
                    .param("password","1234"))
                .andExpect(status().isOk())
                .andExpect(content().string("good!"))
                ;
    }

    @Test
    public void getCouponsFailTest() throws Exception {

        redisTemplate.opsForList().leftPush("token", "1234");
        Coupons coupons = new Coupons();
        coupons.setUsername("screw");
        coupons.setToken((String)redisTemplate.opsForList().rightPop("token"));
        redisTemplate.opsForList().leftPush("coupons",coupons.toString());

        mockMvc.perform(get("/redis/coupons")
                .param("username","seokkyu")
                .param("password","1234"))
                .andExpect(status().isOk())
                .andExpect(content().string("no!!"))
        ;
    }
}