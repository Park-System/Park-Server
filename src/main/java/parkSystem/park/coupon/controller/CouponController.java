package parkSystem.park.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import parkSystem.park.coupon.dto.request.CouponReqDTO;
import parkSystem.park.coupon.service.CouponService;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("")
    public void createCoupon(@RequestBody CouponReqDTO couponReqDTO){
        couponService.createCoupon(couponReqDTO);
    }
}
