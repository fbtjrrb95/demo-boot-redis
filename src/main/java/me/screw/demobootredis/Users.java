package me.screw.demobootredis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue
    private Long Id;

    @OneToOne
    Coupons coupons;

    private String username;
    private String password;
}
