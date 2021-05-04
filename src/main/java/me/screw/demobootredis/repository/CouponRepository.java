package me.screw.demobootredis.repository;

import me.screw.demobootredis.domain.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupons, Long> {
    Optional<Coupons> findById(Long id);

    // hibernate가 만들어주는 쿼리
    List<Coupons> findByUsersUsernameAndUsersPassword(String username, String password);
}
