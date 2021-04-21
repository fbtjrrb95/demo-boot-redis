package me.screw.demobootredis.service;

import me.screw.demobootredis.repository.UsersRedisRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class RedisServiceTest {

    @InjectMocks
    private RedisService redisService;


    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ListOperations<String, String> mockTokenList;

//    @Mock
//    private UsersRedisRepository usersRedisRepository;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(redisTemplate.opsForList()).thenReturn(mockTokenList);

//        Mockito.doNothing().when(mockTokenList).set("token", 1L, "coupon_uuid");
    }

    @Test
    public void initializeToken() {
        Mockito.when(redisTemplate.opsForList().size("token")).thenReturn(10L);
        assertEquals(10L, redisService.initializeToken(10));
    }

//    @Test
//    public void getToken() {
//        redisTemplate.opsForList().rightPush("token","coupon_uuid");
//        assertEquals(redisService.getToken(),"coupon_uuid");
//    }
//
//    @Test
//    public void getNullToken() {
//        assertEquals(redisService.getToken(), null);
//    }

//    @Test
//    public void getUsers(){
//
//    }
//
//    @Test
//    public void setUsers() {
//
//    }
//
//    @Test
//    public void isExist() {
//
//    }

}