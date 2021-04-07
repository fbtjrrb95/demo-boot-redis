package me.screw.demobootredis.service;

import me.screw.demobootredis.domain.Coupons;
import me.screw.demobootredis.domain.Users;
import me.screw.demobootredis.repository.CouponRepository;
import me.screw.demobootredis.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Coupons> getCoupons(String username, String password) throws Exception {
        Optional<List<Coupons>> coupons = Optional.ofNullable(couponRepository.findByUsersUsernameAndUsersPassword(username, password));
        List<Coupons> _coupons = coupons.orElseThrow(() -> new NoSuchElementException("no"));
        return _coupons;
    }
}
