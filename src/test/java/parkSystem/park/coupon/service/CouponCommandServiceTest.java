package parkSystem.park.coupon.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.coupon.domain.Coupon;
import parkSystem.park.coupon.dto.request.CouponReqDTO;
import parkSystem.park.coupon.service.command.CouponCommandService;

@SpringBootTest
@Transactional
class CouponCommandServiceTest {

    @Autowired
    CouponCommandService couponCommandService;

    @Test
    void createCoupon() {
        //given
        CouponReqDTO couponReqDTO = new CouponReqDTO("테스트쿠폰", "2024-10-30", "2099-12-31", 30, 50);
        //when
        Coupon coupon = couponCommandService.createCoupon(couponReqDTO);
        //then
        Assertions.assertThat(coupon.getCouponName()).isEqualTo("테스트쿠폰");
    }
}