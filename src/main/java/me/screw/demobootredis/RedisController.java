package me.screw.demobootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Controller
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/form")
    public String getForm() throws Exception {
        return "events/form";
    }

    @GetMapping("/redis/get")
    @ResponseBody
    public String getKeyValue(@RequestParam String key) throws Exception {
        String value = (String) redisTemplate.opsForValue().get(key);
        return "value is "+value;
    }

    @PostMapping("/redis/post")
    @ResponseBody
    public String postKeyValue(@ModelAttribute Event event) throws Exception {
        String key =event.getKey();
        redisTemplate.opsForValue().set(key, event.getValue());
        return "key is " + key + " saved value is " + redisTemplate.opsForValue().get(key);
    }

    @PostMapping("/redis/token")
    @ResponseBody
    public void initializeToken() throws Exception {
        // java enum으로 1000을 쓸 수 있나
        UUID uuid = null;
        for(int i=0;i<1000;i++) {
            uuid = randomUUID();
            redisTemplate.opsForList().leftPush("token", uuid.toString());
        }
    }

    @GetMapping("/redis/token")
    @ResponseBody
    public long getToken() throws Exception {
        Long token = redisTemplate.opsForList().size("token");
        redisTemplate.opsForList().rightPop("token");
        return token - redisTemplate.opsForList().size("token");
    }

}
