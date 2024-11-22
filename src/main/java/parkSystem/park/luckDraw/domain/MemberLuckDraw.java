package parkSystem.park.luckDraw.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import parkSystem.park.luckDraw.domain.enums.WinnerStatus;
import parkSystem.park.member.domain.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberLuckDraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberLuckDraw_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private WinnerStatus winnerStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "luckDraw_id")
    private LuckDraw luckDraw;
}
