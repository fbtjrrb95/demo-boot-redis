package me.screw.demobootredis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Coupons> couponList;

    @Column(unique = true)
    private String username;

    private String password;
}
