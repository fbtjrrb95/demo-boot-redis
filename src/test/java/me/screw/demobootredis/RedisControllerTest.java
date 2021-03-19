package me.screw.demobootredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    UsersRepository usersRepository;

    @Test
    public void getTokenFailTest() throws Exception {
        mockMvc.perform(post("/redis/token"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/fail"))
                ;
    }

    @Test
    public void getTokenSuccessTest() throws Exception {
        redisTemplate.opsForList().leftPush("token", "1");
        mockMvc.perform(post("/redis/token"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/success"))
        ;
    }

    @Test
    public void setTokenTest() throws Exception {
        mockMvc.perform(get("/token/init"))
                .andExpect(status().isOk())
                .andExpect(content().string("token initialize success!"))
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

        Coupons coupons = new Coupons();
        coupons.setCouponnumber("1234");
        coupons.setUsername("seokkyu");

        Users users = new Users();
        users.setUsername("seokkyu");
        users.setPassword("1234");
        users.setCoupons(coupons);
        coupons.setUsers(users);

        usersRepository.save(users);
        // TODO: 이거 BLOG
        // coupons가 users references 하는 데 두개의 관계를 맺어주더라도 usersRepository에 save해야한다.
        // couponsRepository에 save하니깐 에러난다.
//        couponRepository.save(coupons);

        mockMvc.perform(post("/redis/coupons")
                    .param("username","seokkyu")
                    .param("password","1234"))
                .andExpect(status().isOk())
                .andExpect(content().string("yes"))
                ;
    }

    @Test
    public void getCouponsFailTest() throws Exception {

        redisTemplate.opsForList().leftPush("token", "1234");
        Coupons coupons = new Coupons();
        coupons.setUsername("screw");
        coupons.setCouponnumber((String)redisTemplate.opsForList().rightPop("token"));
        redisTemplate.opsForList().leftPush("coupons",coupons.toString());

        mockMvc.perform(post("/redis/coupons")
                .param("username","seokkyu")
                .param("password","1234"))
                .andExpect(status().isOk())
                .andExpect(content().string("no"))
        ;
    }
}