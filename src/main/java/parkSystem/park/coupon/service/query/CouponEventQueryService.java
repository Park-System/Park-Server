package parkSystem.park.coupon.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.coupon.domain.CouponEvent;
import parkSystem.park.coupon.dto.response.CouponEventListResDTO;
import parkSystem.park.coupon.exception.NotFoundCouponEventException;
import parkSystem.park.coupon.repository.CouponEventRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponEventQueryService {

    private final CouponEventRepository couponEventRepository;

    public List<CouponEventListResDTO> findAllEvents(){
        List<CouponEvent> list = couponEventRepository.findAll();
        return list.stream().map(CouponEventListResDTO::toDto).toList();
    }

    public CouponEvent findCouponEventById(Long eventId) {
        return couponEventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundCouponEventException("쿠폰 이벤트가 존재하지 않습니다 : " + eventId));
    }

}
