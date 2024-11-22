package parkSystem.park.luckDraw.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Prizes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prize_id")
    private Long id;

    private String prizeName;

    private int quantity;

    private int discountRate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "luckDraw_id")
    private LuckDraw luckDraw;


    @Builder
    public Prizes(String prizeName, LuckDraw luckDraw, int quantity, int discountRate, LocalDateTime startDate, LocalDateTime endDate) {
        this.prizeName = prizeName;
        this.luckDraw = luckDraw;
        this.quantity = quantity;
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
