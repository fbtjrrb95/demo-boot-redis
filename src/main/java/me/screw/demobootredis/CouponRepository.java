package me.screw.demobootredis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupons, Long> {
    // TODO: 이거 RETURN Optional빼면 왜 에러가 나는 걸까?
    Optional<Coupons> findById(Long id);
}
