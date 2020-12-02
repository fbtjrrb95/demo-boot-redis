package me.screw.demobootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
}
