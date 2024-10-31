package parkSystem.park.coupon.dto.request;

import lombok.Getter;
import lombok.Setter;
import parkSystem.park.coupon.domain.Coupon;

import java.time.LocalDateTime;

@Getter
@Setter
public class CouponEventReqDTO {
    private String event_title;
    private String content;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private Coupon coupon;
}
