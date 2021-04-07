package me.screw.demobootredis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Coupons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String couponnumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="users_id")
    private Users users;

    @Override
    public String toString() {
        return "Coupons{" +
                "Id=" + Id +
                ", couponnumber='" + couponnumber + '\'' +
                '}';
    }
}
