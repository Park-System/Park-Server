package parkSystem.park.car.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import parkSystem.park.car.domain.enums.CarType;
import parkSystem.park.member.domain.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CarType carType;

    private String carName;

    private String carNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Cars(CarType carType, String carName, String carNumber, Member member) {
        this.carType = carType;
        this.carName = carName;
        this.carNumber = carNumber;
        this.member = member;
    }


}
