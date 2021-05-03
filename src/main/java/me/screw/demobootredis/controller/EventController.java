package me.screw.demobootredis.controller;

import me.screw.demobootredis.Event;
import me.screw.demobootredis.domain.Coupons;
import me.screw.demobootredis.service.JpaService;
import me.screw.demobootredis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@SessionAttributes("event")
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
    public String setToken(Model model) throws Exception {
        // initialize tokens at redis
        redisService.initializeToken(10);
        return "redirect:/form";
    }

    @GetMapping("/form")
    public String getForm() throws Exception {
        return "events/form";
    }

    @PostMapping("/users")
    public String saveUsers(@ModelAttribute Event event, RedirectAttributes redirectAttributes) throws Exception {
        String username = event.getUsername();
        String password = event.getPassword();
        try {
            boolean isUsersExist = redisService.isExist(username);
            if(isUsersExist) {
                return "events/form";
            }
            redisService.setUsers(username);
            jpaService.saveUsers(username, password);
        }catch (Exception e){
            return "events/form";
        }
        redirectAttributes.addFlashAttribute("event",event);
        return "redirect:/success/apply";
    }

    @GetMapping("/success/apply")
    public String submit() {
        return "events/apply";
    }

    /**
     *
     *
     * @return 만약 redis token list에서 발급받을 수 있는 token이 존재한다면 redis coupons list에 저장하고 success view
     * @return 그렇지 않다면 fail view
     * @throws Exception
     */
    @GetMapping("/token")
    public synchronized String getToken(Model model) throws Exception {
        String couponNumber = redisService.getToken();
        Event event = (Event)model.asMap().get("event");
        if(event == null) {
            return "events/fail";
        }
        String username = event.getUsername();
        if(couponNumber == null) {
            return "events/fail";
        }
        jpaService.saveCoupons(couponNumber, username);
        return "events/success";
    }

    @GetMapping("/success")
    public String getToken1(@ModelAttribute Event event) throws Exception {
        return "events/success";
    }

    @PostMapping("/coupons")
    public String getCoupons(@ModelAttribute Event event, Model model){
        String password = event.getPassword();
        String username = event.getUsername();
        try {
            List<Coupons> coupons = jpaService.getCoupons(username, password);
            model.addAttribute("coupons", coupons);
            return "events/coupons";
        } catch(NoSuchElementException e){
            return "events/fail";
        } catch(Exception e){
            return "events/fail";
        }
    }

}
