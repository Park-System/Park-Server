package parkSystem.park.coupon.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CouponReqDTO {
    private String couponName;
    private String start_date;
    private String end_date;
    private int disCountRate;
    private int total_count;

    public CouponReqDTO(String couponName, String start_date, String end_date, int disCountRate, int total_count) {
        this.couponName = couponName;
        this.start_date = start_date;
        this.end_date = end_date;
        this.disCountRate = disCountRate;
        this.total_count = total_count;
    }
}
