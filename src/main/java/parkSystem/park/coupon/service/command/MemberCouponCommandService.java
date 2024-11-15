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
import parkSystem.park.coupon.service.query.CouponEventQueryService;
import parkSystem.park.coupon.service.query.CouponQueryService;
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
    private final CouponQueryService couponQueryService;
    private final CouponEventQueryService couponEventQueryService;
    private final MemberCouponRepository memberCouponRepository;

    public MemberCoupon createCouponEventPublish(CouponEventPublishReqDTO couponEventPublishReqDTO){

        log.info("쿠폰 발행 접근!");
        String username = couponEventPublishReqDTO.username();
        Long eventId = couponEventPublishReqDTO.eventId();

        Member member = memberRepository.findByUsername(username).get();
        CouponEvent couponEvent = couponEventQueryService.findCouponEventById(eventId);
        //LockMode Coupon 쿼리
        Coupon coupon = couponQueryService.findCouponByIdForUpdate(couponEvent.getCoupon().getId());
        //쿠폰 활성화 여부 체크 메서드
        CouponStatus couponStatus = Coupon.getCouponStatus(coupon);

        //쿠폰이 0개 미만일 시, 오류 발생 및 예외 처리 진행
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
