package me.screw.demobootredis.controller;

import me.screw.demobootredis.User;
import me.screw.demobootredis.domain.Coupons;
import me.screw.demobootredis.service.JpaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public JpaService jpaService;

    @Test
    public void getTokenFailTest() throws Exception {
        mockMvc.perform(get("/token"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/fail"))
                ;
    }

    @Test
    public void getTokenSuccessTest() throws Exception {
        User user = new User();
        user.setUsername("seokkyu");
        user.setPassword("1234");
        Coupons coupons = new Coupons();
        coupons.setCouponnumber("coupon_uuid");

        redisTemplate.opsForList().leftPush("token", "1");
        jpaService.saveCoupons("seokkyu","1234");

        mockMvc.perform(get("/token")
                .flashAttr("event", user))
                .andExpect(status().isOk())
                .andExpect(view().name("events/success"))

        ;
    }

    @Test
    public void setTokenTest() throws Exception {
        mockMvc.perform(get("/init"))
                .andExpect(status().is3xxRedirection())
        ;
    }

    @Test
    public void getFormTest() throws Exception {
        mockMvc.perform(get("/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/form"))
                ;
    }

    @Test
    public void getCouponsTest() throws Exception {
        jpaService.saveCoupons("1234", "seokkyu");

        mockMvc.perform(post("/coupons")
                    .param("username","seokkyu")
                    .param("password","1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/coupons"))
                ;
    }

    @Test
    public void saveUsersTest() throws Exception {
        mockMvc.perform(post("/users")
                        .param("username","seokkyu")
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/success/apply"))
                ;
    }

    @Test
    public void getCouponsFailTest() throws Exception {

        redisTemplate.opsForList().leftPush("token", "1234");
        Coupons coupons = new Coupons();

        coupons.setCouponnumber((String)redisTemplate.opsForList().rightPop("token"));
        redisTemplate.opsForList().leftPush("coupons",coupons.toString());

        mockMvc.perform(post("/coupons")
                .param("username","seokkyu")
                .param("password","1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/coupons"))
        ;
    }
}