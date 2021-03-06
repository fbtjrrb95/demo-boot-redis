package me.screw.demobootredis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectController {

    @GetMapping("/redirect/success")
    public RedirectView getRedirectToken(){
        return new RedirectView("/success");
    }

    @GetMapping("/redirect/success/apply")
    public RedirectView getRedirectApply(){
        return new RedirectView("/success/apply");
    }

    @GetMapping("/redirect/form")
    public RedirectView getRedirectForm(){
        return new RedirectView("/form");
    }
}
