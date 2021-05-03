package me.screw.demobootredis.service;

import me.screw.demobootredis.domain.Coupons;
import me.screw.demobootredis.domain.Users;
import me.screw.demobootredis.repository.CouponRepository;
import me.screw.demobootredis.repository.UsersRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class JpaService {

    @Autowired
    public UsersRepository usersRepository;

    @Autowired
    public CouponRepository couponRepository;

    public Users saveUsers(String username, String password) throws Exception{
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(password);
        usersRepository.save(users);
        return users;
    }

    public Coupons saveCoupons(String couponNumber, String username){
        Coupons coupons = new Coupons();
        coupons.setCouponnumber(couponNumber);
        Users users = usersRepository.findByUsername(username);
        coupons.setUsers(users);
        couponRepository.save(coupons);
        return coupons;
    }

    public List<Coupons> getCoupons(String username, String password) throws Exception {
        List<Coupons> coupons = couponRepository.findByUsersUsernameAndUsersPassword(username, password);
//        Users users = usersRepository.findByUsername(username);
//        List<Coupons> coupons = users.getCouponList();
        // 이게 왜 lazy fetch가 안되는 걸까?
//        String ns = coupons.get(0).getUsers().getUsername();
        return coupons;
    }
}
