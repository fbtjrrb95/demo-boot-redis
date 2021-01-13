package me.screw.demobootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Controller
@Transactional
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    CouponRepository couponRepository;

    @GetMapping("/token/init")
    public String setToken(Model model) throws Exception {
        // initialize tokens at redis
        UUID uuid = null;
        for(int i=0;i<1000;i++) {
            uuid = randomUUID();
            redisTemplate.opsForList().leftPush("token", uuid.toString());
        }
        model.addAttribute("tokens", 1000);

        return "events/form";
    }

    @GetMapping("/redis/token")
    public String getToken(@ModelAttribute Event event) throws Exception {

        Long totalSize = redisTemplate.opsForList().size("token");
        String token = (String)redisTemplate.opsForList().rightPop("token");

        String username = event.getUsername();
        String password = event.getPassword();


        //TODO: DB Coupons에 user 이름 저장하는 로직
//        Coupons coupons = new Coupons();
//        coupons.setCoupon("test-coupon");
//        coupons.setUsername("seokkyu");
//        couponRepository.save(coupons);

        return "events/post";
    }

}
