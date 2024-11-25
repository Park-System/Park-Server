package parkSystem.park.luckDraw.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import parkSystem.park.member.domain.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Winners {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "winners_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "luckDraw_id")
    private LuckDraw luckDraw;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prizes_id")
    private Prizes prizes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Winners(Long id, LuckDraw luckDraw, Prizes prizes, Member member) {
        this.id = id;
        this.luckDraw = luckDraw;
        this.prizes = prizes;
        this.member = member;
    }
}
