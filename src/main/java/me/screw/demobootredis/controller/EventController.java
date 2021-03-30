package me.screw.demobootredis.controller;

import me.screw.demobootredis.Event;
import me.screw.demobootredis.domain.Coupons;
import me.screw.demobootredis.service.JpaService;
import me.screw.demobootredis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Controller
@Transactional
public class EventController {

    @ModelAttribute("event")
    public Event event(){
        return new Event();
    }

    @Autowired
    RedisService redisService;

    @Autowired
    JpaService jpaService;

    /*
    *
    * token initializing
    *
    */
    @GetMapping("/init")
    @ResponseBody
    public String setToken(Model model) throws Exception {
        // initialize tokens at redis
        redisService.initializeToken(10);
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
    @PostMapping("/token")
    public String getToken(@ModelAttribute Event event) throws Exception {
        String username = event.getUsername();
        String password = event.getPassword();

        String couponNumber = redisService.getToken();

        if(couponNumber == null) return "events/fail";
        jpaService.saveUsers(username, password, couponNumber);

        return "events/success";
    }

    @GetMapping("/token")
    public String getToken1(@ModelAttribute Event event) throws Exception {
        return "events/success";
    }

    @PostMapping("/coupons")
    public String getCoupons(@ModelAttribute Event event, Model model){
        String password = event.getPassword();
        String username = event.getUsername();
        try {
            Coupons coupons = jpaService.getCoupons(username, password);
            model.addAttribute("coupons", coupons);
            return "events/coupons";
        } catch(NoSuchElementException e){
            return "events/fail";
        } catch(Exception e){
            return "events/fail";
        }
    }

}
