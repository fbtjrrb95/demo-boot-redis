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
import java.util.NoSuchElementException;
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
    @ResponseBody
    public String setToken(Model model) throws Exception {
        // initialize tokens at redis
        UUID uuid = null;
        int num = 10;
        for(int i=0;i<num;i++) {
            uuid = randomUUID();
            redisTemplate.opsForList().leftPush("token", uuid.toString());
        }
        return "token initialize success!";
    }

    @GetMapping("/form")
    public String getForm() throws Exception {
        return "events/form";
    }

    /**
     *
     * @param event username과 password과 form data로 넘어온다.
     * @return 만약 redis token list에서 발급받을 수 있는 token이 존재한다면 redis coupons list에 저장하고 success view
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
            usersRepository.save(users);
            redisTemplate.opsForList().leftPush("coupons", coupons.toString());
            return "events/success";
        }
        return "events/fail";
    }

    @PostMapping("/redis/coupons")
    @ResponseBody
    public String getCoupons(@ModelAttribute Event event){
        String password = event.getPassword();
        String username = event.getUsername();

        // database에서 확인하기
        try {
            // TODO: 이 Jpa transaction을 좀 더 팬시하게 쓸 수 없을까?
            // #1
//            Optional<Users> user = usersRepository.findByUsernameAndPassword(username, password);
//            Users _user = user.orElseThrow(() -> new NoSuchElementException("no"));
//            Optional<Coupons> coupon = Optional.ofNullable(_user.getCoupons());

            // #2
//            Optional<Coupons> coupon = Optional.ofNullable(couponRepository.findByUsersUsernameAndUsersPassword(username, password));

            //#3
            Optional<Coupons> coupon = Optional.ofNullable(couponRepository.findByUsernameAndPassword(username, password));
//            Optional<Coupons> coupon = couponRepository.findById(_user.getCoupons().getId());
            Coupons _coupon = coupon.orElseThrow(() -> new NoSuchElementException("no"));
        }catch(NoSuchElementException e){
            return e.getMessage();
        }
        return "yes";
    }

}
