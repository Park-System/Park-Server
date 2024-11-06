package parkSystem.park.coupon.dto.request;

import parkSystem.park.coupon.domain.Coupon;

import java.time.LocalDateTime;

public record CouponEventReqDTO(

         String event_title,

         String content,

         LocalDateTime start_date,

         LocalDateTime end_date,

         long coupon_id) {

}
