package me.screw.demobootredis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event{
    private String username;
    private String password;

    @Override
    public String toString() {
        return "Event{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
