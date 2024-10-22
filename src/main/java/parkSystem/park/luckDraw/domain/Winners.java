package parkSystem.park.luckDraw.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Winners {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "winners_id")
    private Long id;

    private LocalDateTime winDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prize_id")
    private Prizes prizes;

    public Winners(LocalDateTime winDate, Prizes prizes) {
        this.winDate = winDate;
        this.prizes = prizes;
    }
}
