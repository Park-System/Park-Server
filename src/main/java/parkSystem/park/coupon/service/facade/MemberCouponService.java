package parkSystem.park.coupon.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import parkSystem.park.coupon.domain.MemberCoupon;
import parkSystem.park.coupon.dto.request.CouponEventPublishReqDTO;
import parkSystem.park.coupon.service.command.MemberCouponCommandService;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberCouponCommandService memberCouponCommandService;

    /**
     * 고객에게 쿠폰 발급 로직
     */
    public MemberCoupon createCouponEventPublish(CouponEventPublishReqDTO couponEventPublishReqDTO){
        return memberCouponCommandService.createCouponEventPublish(couponEventPublishReqDTO);
    }

}
