package me.screw.demobootredis.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="users_id")
    private Long Id;

//    @OneToMany(mappedBy = "users",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<Coupons> couponList= new ArrayList<>();

    @Column(unique = true)
    private String username;

    private String password;

//    public void addCoupons(Coupons coupons){
//        this.getCouponList().add(coupons);
//        coupons.setUsers(this);
//    }
}
