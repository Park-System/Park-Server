package parkSystem.park.coupon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.coupon.domain.Coupon;
import parkSystem.park.coupon.dto.request.CouponReqDTO;
import parkSystem.park.coupon.repository.CouponRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CouponService {

    private final CouponRepository couponRepository;
    public Coupon createCoupon(CouponReqDTO couponReqDTO){
        Coupon coupon = Coupon.builder()
                .couponName(couponReqDTO.getCouponName())
                .disCountRate(couponReqDTO.getDisCountRate())
                .startDate(couponReqDTO.getStart_date())
                .endDate(couponReqDTO.getEnd_date())
                .total_count(couponReqDTO.getTotal_count())
                .count(couponReqDTO.getTotal_count())
                .build();

        return couponRepository.save(coupon);
    }
}
