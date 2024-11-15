package parkSystem.park.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parkSystem.park.common.dto.response.CommonResponse;
import parkSystem.park.coupon.dto.request.CouponEventPublishReqDTO;
import parkSystem.park.coupon.service.facade.MemberCouponService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-coupon")
public class MemberCouponController {

    private final MemberCouponService memberCouponService;

    @PostMapping("/publish")
    public ResponseEntity<?> couponEventPublish(@RequestBody CouponEventPublishReqDTO couponEventPublishReqDTO){
        //고객에 쿠폰을 발급
        memberCouponService.createCouponEventPublish(couponEventPublishReqDTO);
        CommonResponse commonResponse = new CommonResponse("200 OK", "쿠폰이 발급되었습니다.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

}
