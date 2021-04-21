package me.screw.demobootredis.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@RedisHash("users")
public class RedisUsers implements Serializable {

    @Id
    private String id;


    @Builder
    public RedisUsers(String username){
        this.id=username;
    }

}
