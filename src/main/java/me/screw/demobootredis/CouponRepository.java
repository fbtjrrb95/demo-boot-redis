package me.screw.demobootredis;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupons, Long> {
    // TODO: 이거 RETURN Optional빼면 왜 에러가 나는 걸까?
    // Optional로 감싸지 않는 것이 더 좋다고 하던데,,
    Optional<Coupons> findById(Long id);

    @Query(value = "select c from Coupons c where c.id = (select u.id from Users u where u.username = :username and u.password = :password)")
    Coupons findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    Coupons findByUsersUsernameAndUsersPassword(String username, String password);
}
