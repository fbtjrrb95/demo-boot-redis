package me.screw.demobootredis.domain;

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

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private Coupons coupons;

    private String username;
    private String password;
}
