package parkSystem.park.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import parkSystem.park.common.dto.response.CommonResponse;
import parkSystem.park.coupon.dto.request.CouponEventReqDTO;
import parkSystem.park.coupon.dto.request.CouponReqDTO;
import parkSystem.park.coupon.service.CouponEventService;

@RestController
@RequiredArgsConstructor
public class CouponEventController {

    private final CouponEventService couponEventService;

    @PostMapping("/event/register")
    public ResponseEntity<CommonResponse> createCoupon(@RequestBody CouponEventReqDTO couponEventReqDTO){
        // 쿠폰 이벤트 등록 로직
        couponEventService.createEvent(couponEventReqDTO);
        CommonResponse commonResponse = new CommonResponse("200 OK", "이벤트가 등록되었습니다.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
