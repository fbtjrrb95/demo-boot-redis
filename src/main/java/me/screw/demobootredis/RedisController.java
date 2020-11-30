package me.screw.demobootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/redis")
    public String getKey() throws Exception {
        String key = "key:springboot";
        redisTemplate.opsForValue().set(key, "Hello");
        String value = (String) redisTemplate.opsForValue().get(key);
        return value;
    }
}
