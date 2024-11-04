package parkSystem.park.coupon.service.couponEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.coupon.domain.Coupon;
import parkSystem.park.coupon.domain.CouponEvent;
import parkSystem.park.coupon.domain.enums.EventStatus;
import parkSystem.park.coupon.dto.request.CouponEventReqDTO;
import parkSystem.park.coupon.repository.CouponEventRepository;
import parkSystem.park.coupon.repository.CouponRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CouponEventService {

    private final CouponEventRepository couponEventRepository;
    private final CouponRepository couponRepository;

    public CouponEvent createEvent(CouponEventReqDTO couponEventReqDTO){

        LocalDateTime startDate = couponEventReqDTO.start_date();
        LocalDateTime endDate = couponEventReqDTO.end_date();
        LocalDateTime now = LocalDateTime.now();
        EventStatus eventStatus;

        // 이벤트 시작, 종료일과 현재일을 비교하여 이벤트의 상태 여부 결정
        if((now.isEqual(startDate) || now.isAfter(startDate)) && (now.isEqual(endDate) || now.isBefore(endDate))){
            eventStatus=EventStatus.ON;
        }else eventStatus=EventStatus.OFF;

        Coupon coupon = couponRepository.findById(couponEventReqDTO.coupon_id()).get();

        CouponEvent couponEvent = CouponEvent.builder()
                .event_title(couponEventReqDTO.event_title())
                .content(couponEventReqDTO.content())
                .start_date(couponEventReqDTO.start_date())
                .end_date(couponEventReqDTO.end_date())
                .status(eventStatus)
                .coupon(coupon)
                .build();

        return couponEventRepository.save(couponEvent);
    }
}
