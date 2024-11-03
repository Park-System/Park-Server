package parkSystem.park.coupon.dto.request;

public record CouponReqDTO(

        String couponName,

        String start_date,

        String end_date,

        int disCountRate,

        int total_count) {

}
