package me.screw.demobootredis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Coupons {

    @Id
    @GeneratedValue
    private Long Id;

    private String couponnumber;
    private String username;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="users_id")
    private Users users;

    @Override
    public String toString() {
        return "Coupons{" +
                "token='" + couponnumber + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
