package me.screw.demobootredis;

import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class DemoBootRedisApplication {
    public static void main(String[] args){
        SpringApplication application = new SpringApplication(DemoBootRedisApplication.class);
        application.run(args);
    }
}
