package me.screw.demobootredis.service;

import me.screw.demobootredis.domain.Coupons;
import me.screw.demobootredis.domain.Users;
import me.screw.demobootredis.repository.CouponRepository;
import me.screw.demobootredis.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class JpaService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CouponRepository couponRepository;

    public void saveUsers(String username, String password, String couponNumber) {
        Coupons coupons = new Coupons();
        coupons.setCouponnumber(couponNumber);
        coupons.setUsername(username);

        Users users = new Users();
        users.setUsername(username);
        users.setPassword(password);
        users.setCoupons(coupons);
        coupons.setUsers(users);

        // TODO: user를 save하는 게 맞는 건가?
        // 아직도 정확한 mapping 개념을 모르나보다,,
        usersRepository.save(users);
    }

    public Coupons getCoupons(String username, String password) throws Exception {
        // #1
//            Optional<Users> user = usersRepository.findByUsernameAndPassword(username, password);
//            Users _user = user.orElseThrow(() -> new NoSuchElementException("no"));
//            Optional<Coupons> coupon = Optional.ofNullable(_user.getCoupons());
//            Optional<Coupons> coupon = couponRepository.findById(_user.getCoupons().getId());
        // #2
//            Optional<Coupons> coupon = Optional.ofNullable(couponRepository.findByUsersUsernameAndUsersPassword(username, password));

        //#3
        Optional<Coupons> coupon = Optional.ofNullable(couponRepository.findByUsernameAndPassword(username, password));
        Coupons _coupon = coupon.orElseThrow(() -> new NoSuchElementException("no"));
        return _coupon;
    }
}