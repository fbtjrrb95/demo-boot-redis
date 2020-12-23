package me.screw.demobootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @Autowired
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setCoupon("coupon");
        account.setUsername("seokkyu");
        entityManager.persist(account);
    }
}
