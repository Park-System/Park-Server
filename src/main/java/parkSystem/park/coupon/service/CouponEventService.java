package parkSystem.park.coupon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.coupon.domain.CouponEvent;
import parkSystem.park.coupon.domain.enums.EventStatus;
import parkSystem.park.coupon.dto.request.CouponEventReqDTO;
import parkSystem.park.coupon.repository.CouponEventRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CouponEventService {

    private final CouponEventRepository couponEventRepository;

    public CouponEvent createEvent(CouponEventReqDTO couponEventReqDTO){

        LocalDateTime startDate = couponEventReqDTO.getStart_date();
        LocalDateTime endDate = couponEventReqDTO.getEnd_date();
        LocalDateTime now = LocalDateTime.now();
        EventStatus eventStatus;
        if((now.isEqual(startDate) || now.isBefore(startDate)) && (now.isEqual(endDate) || now.isAfter(endDate))){
            eventStatus=EventStatus.ON;
        }else eventStatus=EventStatus.OFF;

        CouponEvent couponEvent = CouponEvent.builder()
                .event_title(couponEventReqDTO.getEvent_title())
                .content(couponEventReqDTO.getContent())
                .start_date(couponEventReqDTO.getStart_date())
                .end_date(couponEventReqDTO.getEnd_date())
                .status(eventStatus)
                .coupon(couponEventReqDTO.getCoupon())
                .build();

        return couponEventRepository.save(couponEvent);
    }
}
