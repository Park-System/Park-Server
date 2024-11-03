package parkSystem.park.coupon.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import parkSystem.park.coupon.domain.enums.CouponStatus;

import java.time.LocalDateTime;
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

    @Enumerated(EnumType.STRING)
    private CouponStatus status;
    private int total_count;
    private int count;

    @OneToOne(mappedBy = "coupon", fetch = FetchType.LAZY)
    private CouponEvent couponEvent;

    @Builder
    public Coupon(String couponName, int disCountRate, String startDate, String endDate, CouponStatus status, int total_count, int count) {
        this.couponName = couponName;
        this.disCountRate = disCountRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.total_count = total_count;
        this.count = count;
    }
}
