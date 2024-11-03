package parkSystem.park.coupon.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import parkSystem.park.coupon.domain.enums.EventStatus;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private String event_title;

    private String content;

    private LocalDateTime start_date;

    private LocalDateTime end_date;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "coupon_id")
    Coupon coupon;

    @Builder
    public CouponEvent(String event_title, String content, LocalDateTime start_date, LocalDateTime end_date, EventStatus status, Coupon coupon) {
        this.event_title = event_title;
        this.content = content;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.coupon = coupon;
    }
}
