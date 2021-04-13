package me.screw.demobootredis.service;

import me.screw.demobootredis.domain.Coupons;
import me.screw.demobootredis.domain.Users;
import me.screw.demobootredis.repository.CouponRepository;
import me.screw.demobootredis.repository.UsersRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
class JpaServiceTest {

    @InjectMocks
    JpaService jpaService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private UsersRepository usersRepository;

    @Test
    void saveUsers() {

    }

    @Test
    void saveCoupons() {

    }

    @Test
    void getCoupons() throws Exception {
        Coupons coupons = new Coupons();
        coupons.setCouponnumber("coupon_uuid");
        coupons.setId(1l);
        List<Coupons> mockList = new ArrayList<>();
        mockList.add(coupons);
        given(couponRepository.findByUsersUsernameAndUsersPassword("seokkyu","1234"))
                .willReturn(mockList);
        List<Coupons> couponsList = jpaService.getCoupons("seokkyu", "1234");

        assertEquals(couponsList.size(), 1);
        assertEquals(couponsList.get(0).getCouponnumber(), "coupon_uuid");
    }
}