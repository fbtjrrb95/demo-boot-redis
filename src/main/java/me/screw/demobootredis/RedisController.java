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
import java.util.Optional;
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

    /*
    *
    * token initializing
    *
    */
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

    /**
     *
     * @param event username과 password과 form data로 넘어온다.
     * @return 만약 redis token list에서 발급받을 수 있는 token이 존재한다면 redist coupons list에 저장하고 success view
     * @return 그렇지 않다면 fail view
     * @throws Exception
     */
    @PostMapping("/redis/token")
    public String getToken(@ModelAttribute Event event) throws Exception {
        String username = event.getUsername();
        String password = event.getPassword();

        long totalSize = redisTemplate.opsForList().size("token");
        if(totalSize > 0l) {

            String token = (String) redisTemplate.opsForList().rightPop("token");

            Coupons coupons = new Coupons();
            coupons.setCouponnumber(token);
            coupons.setUsername(username);

            Users users = new Users();
            users.setUsername(username);
            users.setPassword(password);
            users.setCoupons(coupons);
            coupons.setUsers(users);

            // TODO: persist to database
            couponRepository.save(coupons);

            redisTemplate.opsForList().leftPush("coupons", coupons.toString());
            return "events/success";
        }
        return "events/fail";
    }

    @GetMapping("/redis/coupons")
    @ResponseBody
    public String getCoupons(@ModelAttribute Event event){
        // TODO: url querystring에 password 넘어오는 것 방지해보기
        String password = event.getPassword();
        String username = event.getUsername();

        // database에서 확인하기
        Optional<Users> user = usersRepository.findByUsernameAndPassword(username, password);

        // TODO: optional api 잘 사용해보기
        if(user.isPresent()) {
            Optional<Coupons> coupon = couponRepository.findById(user.get().getCoupons().getId());
            if(coupon.isPresent()) {
                return "yes";
            }
        }

        // redis에서 확인하기
//
//        List<String> list = (List<String>) redisTemplate.opsForList().range("coupons", 0, -1);
//
//        for(String coupon: list){
//            if(coupon.contains(username) && coupon.contains(password)) return "good!";
//        }
        return "no!!";
    }

}
