package me.screw.demobootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    // jpa 사용
//    @Autowired
//    EntityManager entityManager;

    // spring data jpa 사용
//    @Autowired
//    CouponRepository couponRepository;
//
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Coupons coupons = new Coupons();
//        coupons.setCoupon("coupon");
//        coupons.setUsername("seokkyu");
//        entityManager.persist(coupons);
//        couponRepository.save(coupons);
    }
}
