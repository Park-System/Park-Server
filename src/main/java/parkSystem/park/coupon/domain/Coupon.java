package parkSystem.park.coupon.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import parkSystem.park.coupon.domain.enums.CouponStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    private String couponName;

    private int disCountRate;

    private String startDate;

    private String endDate;

    private int total_count;

    private int count;

    @OneToOne(mappedBy = "coupon", fetch = FetchType.LAZY)
    private CouponEvent couponEvent;

    @Builder
    public Coupon(String couponName, int disCountRate, String startDate, String endDate, int total_count, int count) {
        this.couponName = couponName;
        this.disCountRate = disCountRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total_count = total_count;
        this.count = count;
    }

    public void updateCount(int count){
        this.count=count;
    }

    public static CouponStatus getCouponStatus(Coupon coupon) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime startDate = LocalDateTime.parse(coupon.getStartDate(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(coupon.getEndDate(), formatter);
        LocalDateTime now = LocalDateTime.now();
        CouponStatus couponStatus;

        // 쿠폰 시작, 종료일과 현재일을 비교하여 이벤트의 상태 여부 결정
        if((now.isEqual(startDate) || now.isAfter(startDate)) && (now.isEqual(endDate) || now.isBefore(endDate))){
            couponStatus=CouponStatus.ON;
        }else couponStatus=CouponStatus.OFF;

        return couponStatus;
    }

}
