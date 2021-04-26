package me.screw.demobootredis.service;

import me.screw.demobootredis.repository.UsersRedisRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(redisTemplate.opsForList()).thenReturn(mockTokenList);
        Mockito.when(redisTemplate.opsForList().size("token")).thenReturn(2l);
    }

    @Test
    public void initializeToken() {
        assertEquals(2L, redisService.initializeToken(2));
    }

    @Test
    public void getToken() {
        Mockito.when(redisTemplate.opsForList().size("token")).thenReturn(2L);
        Mockito.when(redisTemplate.opsForList().rightPop("token")).thenReturn("coupon_uuid1");
        assertEquals("coupon_uuid1", redisService.getToken());
    }

    @Test
    public void getNullToken() {
        Mockito.when(redisTemplate.opsForList().size("token")).thenReturn(0L);
        Mockito.when(redisTemplate.opsForList().rightPop("token")).thenReturn(null);
        assertEquals(redisService.getToken(), null);
    }

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