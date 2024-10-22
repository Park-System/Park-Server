package parkSystem.park.luckDraw.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "luckDraw_id")
    private LuckDraw luckDraw;


    public Prizes(String prizeName, LuckDraw luckDraw, int quantity) {
        this.prizeName = prizeName;
        this.luckDraw = luckDraw;
        this.quantity = quantity;
    }
}
