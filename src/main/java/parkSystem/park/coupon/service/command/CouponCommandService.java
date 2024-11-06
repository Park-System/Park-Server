package parkSystem.park.coupon.service.command;

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
public class CouponCommandService {

    private final CouponRepository couponRepository;
    public Coupon createCoupon(CouponReqDTO couponReqDTO){
        Coupon coupon = Coupon.builder()
                .couponName(couponReqDTO.couponName())
                .disCountRate(couponReqDTO.disCountRate())
                .startDate(couponReqDTO.start_date())
                .endDate(couponReqDTO.end_date())
                .total_count(couponReqDTO.total_count())
                .count(couponReqDTO.total_count())
                .build();

        return couponRepository.save(coupon);
    }
}
