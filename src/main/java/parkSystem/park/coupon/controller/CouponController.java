package parkSystem.park.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import parkSystem.park.common.dto.response.CommonResponse;
import parkSystem.park.coupon.dto.request.CouponReqDTO;
import parkSystem.park.coupon.service.CouponService;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupon/register")
    public ResponseEntity<CommonResponse> createCoupon(@RequestBody CouponReqDTO couponReqDTO){
        // 쿠픈 등록 로직
        couponService.createCoupon(couponReqDTO);
        CommonResponse commonResponse = new CommonResponse("200 OK", "쿠폰이 등록되었습니다.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
