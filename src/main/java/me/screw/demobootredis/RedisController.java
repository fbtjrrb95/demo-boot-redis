package me.screw.demobootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Controller
@Transactional
public class RedisController {

    @ModelAttribute("event")
    public Event event(){
        return new Event();
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/token/init")
    public String setToken(Model model) throws Exception {
        // initialize tokens at redis
        UUID uuid = null;
        int num = 10;
        for(int i=0;i<num;i++) {
            uuid = randomUUID();
            redisTemplate.opsForList().leftPush("token", uuid.toString());
        }
        model.addAttribute("tokens", num);

        return "events/form";
    }

    @PostMapping("/redis/token")
    public String getToken(@ModelAttribute Event event) throws Exception {
        System.out.println(event);
        String username = event.getUsername();
        String password = event.getPassword();

        Long totalSize = redisTemplate.opsForList().size("token");
        if(totalSize > 0l) {
            String token = (String) redisTemplate.opsForList().rightPop("token");
            Coupons coupons = new Coupons();
            coupons.setToken(token);
            coupons.setUsername(username);
            redisTemplate.opsForList().leftPush("coupons", coupons.toString());
            return "events/success";
        }
        return "events/fail";
    }

    @GetMapping("/redis/coupons")
    @ResponseBody
    public String getCoupons(@ModelAttribute Event event){
        String password = event.getPassword();
        String username = event.getUsername();

        List<String> list = (List<String>) redisTemplate.opsForList().range("coupons", 0, -1);
        assert list != null;
        for(String coupon: list){
            System.out.println(coupon);
            if(coupon.contains(username)) return "good!";
        }
        return "no!!";
    }

}
