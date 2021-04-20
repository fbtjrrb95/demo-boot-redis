package me.screw.demobootredis.repository;

import me.screw.demobootredis.domain.RedisUsers;
import org.springframework.data.repository.CrudRepository;

public interface UsersRedisRepository extends CrudRepository<RedisUsers, String> {
}
