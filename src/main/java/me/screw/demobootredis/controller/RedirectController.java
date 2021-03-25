package me.screw.demobootredis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectController {

    @GetMapping("/redirect/token")
    public RedirectView getRedirectToken(){
        return new RedirectView("/token");
    }

    @GetMapping("/redirect/form")
    public RedirectView getRedirectForm(){
        return new RedirectView("/form");
    }
}
