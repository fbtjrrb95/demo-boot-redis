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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    void saveUsers() throws Exception {
        Users users = jpaService.saveUsers("seokkyu","1234");
        verify(usersRepository, times(1)).save(users);
    }

    @Test
    void saveCoupons() {
        Users users = new Users();
        users.setId(1l);
        users.setUsername("seokkyu");
        users.setPassword("1234");

        given(usersRepository.findByUsername("seokkyu")).willReturn(users);

        Coupons coupons = jpaService.saveCoupons("coupon_uuid", "seokkyu");
        verify(couponRepository, times(1)).save(coupons);
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