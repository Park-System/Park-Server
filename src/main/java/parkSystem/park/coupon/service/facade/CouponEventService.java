package parkSystem.park.coupon.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import parkSystem.park.coupon.domain.CouponEvent;
import parkSystem.park.coupon.dto.request.CouponEventReqDTO;
import parkSystem.park.coupon.dto.response.CouponEventListResDTO;
import parkSystem.park.coupon.service.couponEvent.CouponEventCommandService;
import parkSystem.park.coupon.service.couponEvent.CouponEventQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponEventService {

    private final CouponEventQueryService couponEventQueryService;
    private final CouponEventCommandService couponEventCommandService;

    /**
     * 쿠폰 이벤트 목록 조회
     */
    public List<CouponEventListResDTO> findAllEvents(){
        return couponEventQueryService.findAllEvents();
    }

    /**
     * 쿠폰 이벤트 생성
     */
    public CouponEvent createEvent(CouponEventReqDTO couponEventReqDTO){
        return couponEventCommandService.createEvent(couponEventReqDTO);
    }
}
