package parkSystem.park.coupon.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
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

    private Integer disCountRate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    @OneToMany(mappedBy = "coupon")
    private List<MemberCoupon> memberCoupons = new ArrayList<>();

    @OneToOne(mappedBy = "coupon", fetch = FetchType.LAZY)
    private CouponEvent couponEvent;

    public Coupon(String couponName, LocalDateTime startDate, Integer disCountRate, LocalDateTime endDate, CouponStatus status) {
        this.couponName = couponName;
        this.startDate = startDate;
        this.disCountRate = disCountRate;
        this.endDate = endDate;
        this.status = status;
    }
}
