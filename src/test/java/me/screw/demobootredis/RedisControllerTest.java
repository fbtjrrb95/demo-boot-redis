package me.screw.demobootredis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@AutoConfigureMockMvc
public class RedisControllerTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getFormTest() throws Exception {
        mockMvc.perform(get("/form"))
                .andExpect(view().name("events/form"));
    }

    @Test
    public void postTest() throws Exception {
        mockMvc.perform(post("/redis/post")
                .param("key","me")
                .param("value", "screw"))
                .andExpect(content().string("key is me saved value is screw"))
        ;
    }

    @Test
    public void getTest() throws Exception {
        redisTemplate.opsForValue().set("me", "screw");
        mockMvc.perform(get("/redis/get")
            .param("key","me"))
            .andExpect(status().isOk())
            .andExpect(content().string("value is screw"))
        ;
    }

    @Test
    public void initializeTokenTest() throws Exception {
        mockMvc.perform(post("/redis/token2"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTokenTest() throws Exception {
        redisTemplate.opsForList().leftPush("token","1");
        mockMvc.perform(get("/redis/token"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                ;
    }
}