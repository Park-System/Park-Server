package parkSystem.park.park.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import parkSystem.park.park.domain.enums.ParkingType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_spot_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    private ParkingType parkingType;

    private String parkingSpotName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_info_id")
    private ParkingInfo parkingInfo;

    @Builder
    public ParkingSpot(String parkingSpotName, ParkingInfo parkingInfo) {
        this.parkingSpotName = parkingSpotName;
        this.parkingInfo = parkingInfo;
    }

}
