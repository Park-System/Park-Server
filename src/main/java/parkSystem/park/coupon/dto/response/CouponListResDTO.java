package parkSystem.park.coupon.dto.response;

import parkSystem.park.coupon.domain.Coupon;

public record CouponListResDTO(

        String couponName,

        int disCountRate,

        String start_date,

        String end_date,

        int total_count,

        int count) {

    public static CouponListResDTO toDto(Coupon coupon){
        return new CouponListResDTO(
                coupon.getCouponName(), coupon.getDisCountRate(), coupon.getStartDate(), coupon.getEndDate(),
                coupon.getTotal_count(), coupon.getCount()
        );
    }
}
