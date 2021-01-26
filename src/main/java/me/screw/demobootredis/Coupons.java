package me.screw.demobootredis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

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

    @OneToOne(mappedBy = "coupons", cascade = CascadeType.ALL)
    private Users users;

    @Override
    public String toString() {
        return "Coupons{" +
                "token='" + couponnumber + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
