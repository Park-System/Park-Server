package parkSystem.park.luckDraw.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LuckDraw {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "luckDraw_id")
    private Long id;

    private String drawTitle;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public LuckDraw(String drawTitle, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.drawTitle = drawTitle;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
