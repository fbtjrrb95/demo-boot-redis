package me.screw.demobootredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoBootRedisApplication {
    public static void main(String[] args){
        SpringApplication application = new SpringApplication(DemoBootRedisApplication.class);
        application.run(args);
    }
}
