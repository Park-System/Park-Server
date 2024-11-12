package parkSystem.park.coupon.service.command;

import jakarta.annotation.Nullable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.coupon.domain.Coupon;
import parkSystem.park.coupon.domain.CouponEvent;
import parkSystem.park.coupon.domain.enums.EventStatus;
import parkSystem.park.coupon.dto.request.CouponEventPublishReqDTO;
import parkSystem.park.coupon.exception.CouponEmptyException;
import parkSystem.park.coupon.repository.CouponEventRepository;
import parkSystem.park.coupon.repository.CouponRepository;
import parkSystem.park.member.domain.Member;
import parkSystem.park.member.domain.enums.UserRole;
import parkSystem.park.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MemberCouponCommandServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberCouponCommandService memberCouponCommandService;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    CouponEventRepository couponEventRepository;

    @BeforeEach
    public void setUp() throws InterruptedException {
        //given
        Coupon coupon = Coupon.builder()
                .count(50)
                .couponName("test")
                .total_count(50)
                .startDate("2024-11-01 00:00:00.000000")
                .endDate("2024-12-31 00:00:00.000000")
                .disCountRate(50)
                .build();
        couponRepository.save(coupon);

        String start = "2024-10-30";
        String end = "2024-12-31";

        // 문자열을 LocalDateTime으로 변환
        LocalDateTime start_date = LocalDateTime.parse(start + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime end_date = LocalDateTime.parse(end + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        CouponEvent couponEvent = CouponEvent.builder()
                .status(EventStatus.ON)
                .start_date(start_date)
                .end_date(end_date)
                .event_title("test event")
                .content("test!!!")
                .coupon(coupon)
                .build();

        couponEventRepository.save(couponEvent);

        // 100명 회원 생성
        for (int i = 1; i <= 100; i++) {
            Member member = new Member("testpwd", "test@test", "user"+i, "test"+i, UserRole.USER, null);
            memberRepository.save(member);
        }

        List<Member> all = memberRepository.findAll();
        for (Member member : all) {
            System.out.println(member.getUsername());
        }

    }

    @Test
    void createCouponEventPublish() {
        //when
        for(int i=1; i<=50; i++){
            CouponEventPublishReqDTO couponEventPublishReqDTO = new CouponEventPublishReqDTO("user"+i, 1L);
            memberCouponCommandService.createCouponEventPublish(couponEventPublishReqDTO);
            Coupon coupon = couponRepository.findById(1L).get();
            System.out.println(coupon.getCount());
        }

        //then
        Coupon coupon = couponRepository.findById(1L).get();
        Assertions.assertThat(coupon.getCount()).isEqualTo(0);
    }

    @Test
    void createCouponEventPublish2() throws InterruptedException {
        //when
        ExecutorService executorsService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(100);

        for(int i=1; i<=100; i++){
            final int memberId = i;
            executorsService.submit(()->{
                try {
                    CouponEventPublishReqDTO couponEventPublishReqDTO = new CouponEventPublishReqDTO("user"+memberId, 1L);
                    memberCouponCommandService.createCouponEventPublish(couponEventPublishReqDTO);
                }catch (CouponEmptyException e){
                    System.out.println("쿠폰 소진!!!");
                }finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        //then
        Coupon coupon = couponRepository.findById(1L).get();
        Assertions.assertThat(coupon.getCount()).isEqualTo(0);
    }
}