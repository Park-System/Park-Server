package parkSystem.park.coupon.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.coupon.domain.Coupon;
import parkSystem.park.coupon.domain.CouponEvent;
import parkSystem.park.coupon.domain.MemberCoupon;
import parkSystem.park.coupon.domain.enums.CouponStatus;
import parkSystem.park.coupon.dto.request.CouponEventPublishReqDTO;
import parkSystem.park.coupon.exception.CouponEmptyException;
import parkSystem.park.coupon.repository.CouponEventRepository;
import parkSystem.park.coupon.repository.CouponRepository;
import parkSystem.park.coupon.repository.MemberCouponRepository;
import parkSystem.park.member.domain.Member;
import parkSystem.park.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberCouponCommandService {

    private final MemberRepository memberRepository;
    private final CouponEventRepository couponEventRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    public MemberCoupon createCouponEventPublish(CouponEventPublishReqDTO couponEventPublishReqDTO){
        log.info("쿠폰 발행 접근!");
        String username = couponEventPublishReqDTO.username();
        Long eventId = couponEventPublishReqDTO.eventId();

        Member member = memberRepository.findByUsername(username).get();
        CouponEvent couponEvent = couponEventRepository.findById(eventId).get();
        //LockMode Coupon 쿼리
        Coupon coupon = couponRepository.findByIdForUpdate(couponEvent.getCoupon().getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime startDate = LocalDateTime.parse(coupon.getStartDate(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(coupon.getEndDate(), formatter);
        LocalDateTime now = LocalDateTime.now();
        CouponStatus couponStatus;

        // 쿠폰 시작, 종료일과 현재일을 비교하여 이벤트의 상태 여부 결정
        if((now.isEqual(startDate) || now.isAfter(startDate)) && (now.isEqual(endDate) || now.isBefore(endDate))){
            couponStatus=CouponStatus.ON;
        }else couponStatus=CouponStatus.OFF;

        if(coupon.getCount()-1<0){
            throw new CouponEmptyException("쿠폰이 모두 소진되었습니다.");
        }

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .member(member)
                .coupon(coupon)
                .count(coupon.getCount()-1)
                .status(couponStatus)
                .build();

        coupon.updateCount(coupon.getCount()-1);

        return memberCouponRepository.save(memberCoupon);
    }
}
