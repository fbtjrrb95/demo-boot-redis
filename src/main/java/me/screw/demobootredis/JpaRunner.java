package me.screw.demobootredis;

import me.screw.demobootredis.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
