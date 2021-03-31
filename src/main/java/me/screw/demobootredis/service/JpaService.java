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

    public Users saveUsers(String username, String password) throws Exception{
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(password);
        usersRepository.save(users);

        return users;
    }
    public void saveCoupons(String couponNumber, String username){
        Coupons coupons = new Coupons();
        coupons.setCouponnumber(couponNumber);
        // persist
        Users users = usersRepository.findByUsername(username);
        coupons.setUsers(users);
        couponRepository.save(coupons);
    }
    public Coupons getCoupons(String username, String password) throws Exception {
        // #1
//        Optional<Users> user = usersRepository.findByUsernameAndPassword(username, password);
//        Users _user = user.orElseThrow(() -> new NoSuchElementException("no"));
//        Optional<Coupons> coupon = Optional.ofNullable(_user.getCoupons());

        // #2
            Optional<Coupons> coupon = Optional.ofNullable(couponRepository.findByUsersUsernameAndUsersPassword(username, password));

        //#3
//        Optional<Coupons> coupon = Optional.ofNullable(couponRepository.findByUsernameAndPassword(username, password));


        Coupons _coupon = coupon.orElseThrow(() -> new NoSuchElementException("no"));
        return _coupon;
    }
}
