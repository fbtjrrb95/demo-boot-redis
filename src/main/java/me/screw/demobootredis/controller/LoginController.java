package me.screw.demobootredis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/login")
public class LoginController {

    @GetMapping("/loginGet")
    public String loginGet(){
        return "events/form";
    }
}
