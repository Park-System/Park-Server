package parkSystem.park.coupon.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import parkSystem.park.coupon.domain.Coupon;
import parkSystem.park.coupon.dto.request.CouponReqDTO;
import parkSystem.park.coupon.dto.response.CouponListResDTO;
import parkSystem.park.coupon.service.coupon.CouponCommandService;
import parkSystem.park.coupon.service.coupon.CouponQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponCommandService couponCommandService;
    private final CouponQueryService couponQueryService;

    /**
     * 쿠폰 목록 전체 조회
     */
    public List<CouponListResDTO> findAllCoupons(){
        return couponQueryService.findAllCoupons();
    }

    /**
     * 쿠폰 생성
     */
    public Coupon createCoupon(CouponReqDTO couponReqDTO){
        return couponCommandService.createCoupon(couponReqDTO);
    }
}
