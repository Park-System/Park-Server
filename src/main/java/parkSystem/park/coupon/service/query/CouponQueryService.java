package parkSystem.park.coupon.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.coupon.domain.Coupon;
import parkSystem.park.coupon.dto.response.CouponListResDTO;
import parkSystem.park.coupon.repository.CouponRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponQueryService {

    private final CouponRepository couponRepository;

    public List<CouponListResDTO> findAllCoupons(){
        List<Coupon> list = couponRepository.findAll();
        return list.stream().map(CouponListResDTO::toDto).toList();
    }
}
