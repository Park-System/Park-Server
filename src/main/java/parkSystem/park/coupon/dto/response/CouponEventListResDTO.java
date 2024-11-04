package parkSystem.park.coupon.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import parkSystem.park.coupon.domain.Coupon;
import parkSystem.park.coupon.domain.CouponEvent;
import parkSystem.park.coupon.domain.enums.EventStatus;

import java.time.LocalDateTime;

public record CouponEventListResDTO(

        String event_title,

        String content,

        LocalDateTime start_date,

        LocalDateTime end_date,

        EventStatus eventStatus,

        CouponListResDTO couponListResDTO) {

    public static CouponEventListResDTO toDto(CouponEvent couponEvent){
        return new CouponEventListResDTO(
                couponEvent.getEvent_title(), couponEvent.getContent(), couponEvent.getStart_date(),
                couponEvent.getEnd_date(), couponEvent.getStatus(), CouponListResDTO.toDto(couponEvent.getCoupon())
        );
    }
}
