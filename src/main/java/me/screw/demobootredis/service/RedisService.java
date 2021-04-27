package me.screw.demobootredis.service;

import me.screw.demobootredis.domain.RedisUsers;
import me.screw.demobootredis.repository.UsersRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.UUID.randomUUID;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UsersRedisRepository usersRedisRepository;

    public RedisService(RedisTemplate redisTemplate, UsersRedisRepository usersRedisRepository) {
        this.redisTemplate = redisTemplate;
        this.usersRedisRepository = usersRedisRepository;
    }

    public long initializeToken(int totalCnt){
        for(int i=0;i<totalCnt;i++) {
            redisTemplate.opsForList().leftPush("token", randomUUID().toString());
        }
        return (long)redisTemplate.opsForList().size("token");
    }

    public String getToken() {
        long totalSize = redisTemplate.opsForList().size("token");
        if(totalSize > 0l) {
            return (String) redisTemplate.opsForList().rightPop("token");
        }
        return null;
    }

    public RedisUsers getUsers(String username) throws Exception{
        Optional<RedisUsers> usersOpt = usersRedisRepository.findById(username);
        return usersOpt.orElseGet(()->null);
    }

    public RedisUsers setUsers(String username) throws Exception{
        RedisUsers users = RedisUsers.builder().username(username).build();
        usersRedisRepository.save(users);
        return users;
    }

    public boolean isExist(String username) throws Exception {
        Optional<RedisUsers> usersOpt = usersRedisRepository.findById(username);
        return usersOpt.isPresent();
    }
}
