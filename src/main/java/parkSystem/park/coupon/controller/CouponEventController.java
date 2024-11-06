package parkSystem.park.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parkSystem.park.common.dto.response.CommonResponse;
import parkSystem.park.coupon.dto.request.CouponEventReqDTO;
import parkSystem.park.coupon.dto.response.CouponEventListResDTO;
import parkSystem.park.coupon.service.facade.CouponEventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class CouponEventController {

    private final CouponEventService couponEventService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> createCouponEvent(@RequestBody CouponEventReqDTO couponEventReqDTO){
        // 쿠폰 이벤트 등록 로직
        couponEventService.createEvent(couponEventReqDTO);
        CommonResponse commonResponse = new CommonResponse("200 OK", "이벤트가 등록되었습니다.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CouponEventListResDTO>> couponEventList(){

        List<CouponEventListResDTO> allEvents = couponEventService.findAllEvents();
        return new ResponseEntity<>(allEvents, HttpStatus.OK);
    }
}
