package parkSystem.park.coupon.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.coupon.domain.Coupon;
import parkSystem.park.coupon.domain.CouponEvent;
import parkSystem.park.coupon.domain.enums.EventStatus;
import parkSystem.park.coupon.dto.request.CouponEventReqDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@Transactional
class CouponEventServiceTest {

    @Autowired
    CouponEventService couponEventService;

    @Test
    void createEvent() {

        //given
        Coupon coupon = Coupon.builder()
                .couponName("테스트쿠폰")
                .disCountRate(50)
                .startDate("2024-10-31")
                .endDate("2024-12-31")
                .total_count(30)
                .count(30)
                .build();

        // 문자열
        String start = "2024-10-30";
        String end = "2024-12-31";

        // 문자열을 LocalDateTime으로 변환
        LocalDateTime start_date = LocalDateTime.parse(start + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime end_date = LocalDateTime.parse(end + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        CouponEventReqDTO couponEventReqDTO = new CouponEventReqDTO("테스트 이벤트", "주차장 선착순 쿠폰 이벤트", start_date, end_date, coupon);

        //when
        CouponEvent event = couponEventService.createEvent(couponEventReqDTO);

        //then
        Assertions.assertThat(event.getCoupon().getCouponName()).isEqualTo("테스트쿠폰");
        Assertions.assertThat(event.getEvent_title()).isEqualTo("테스트 이벤트");
        Assertions.assertThat(event.getStatus()).isEqualTo(EventStatus.ON);

    }
}