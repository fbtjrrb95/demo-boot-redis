package me.screw.demobootredis.controller;

import me.screw.demobootredis.User;
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
@SessionAttributes("user")
public class EventController {

    @ModelAttribute("user")
    public User user(){
        return new User();
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
    public String saveUsers(@ModelAttribute User user, RedirectAttributes redirectAttributes) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();
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
        redirectAttributes.addFlashAttribute("user",user);
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
        User user = (User)model.asMap().get("user");
        if(user == null) {
            return "events/fail";
        }
        String username = user.getUsername();
        if(couponNumber == null) {
            return "events/fail";
        }
        jpaService.saveCoupons(couponNumber, username);
        return "events/success";
    }

    @GetMapping("/success")
    public String getToken1(@ModelAttribute User user) throws Exception {
        return "events/success";
    }

    @PostMapping("/coupons")
    public String getCoupons(@ModelAttribute User user, Model model){
        String password = user.getPassword();
        String username = user.getUsername();
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
