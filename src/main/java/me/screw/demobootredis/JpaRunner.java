package me.screw.demobootredis;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    // spring data jpa 사용
    @Autowired
    CouponRepository couponRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Coupons coupons = new Coupons();
//        coupons.setCouponnumber("couponnumber");
//        coupons.setUsername("seokkyu");
//        couponRepository.save(coupons);
    }
}
