package me.screw.demobootredis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Coupons {

    @Id
    @GeneratedValue
    private Long Id;

    private String token;

    private String username;

    private String coupon;

}
