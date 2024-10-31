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


    public CouponEventReqDTO(String event_title, String content, LocalDateTime start_date, LocalDateTime end_date, Coupon coupon) {
        this.event_title = event_title;
        this.content = content;
        this.start_date = start_date;
        this.end_date = end_date;
        this.coupon = coupon;
    }
}
