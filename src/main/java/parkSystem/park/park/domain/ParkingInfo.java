package parkSystem.park.park.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ParkingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_info_id")
    private Long id;

    private String parkingName;

    private String parkingAddress;

    private String parkType;

    private Integer parkingAmount;

    private String weekDaysStartTime;

    private String weekDaysEndTime;

    private String weekendStartTime;

    private String weekendEndTime;

    private String basicRate;

    private String dayTicketPrice;

    private String specialNote;

    public ParkingInfo(String parkingName, String parkingAddress, String parkType, Integer parkingAmount, String weekDaysStartTime, String weekDaysEndTime, String weekendStartTime, String weekendEndTime, String basicRate, String dayTicketPrice, String specialNote) {
        this.parkingName = parkingName;
        this.parkingAddress = parkingAddress;
        this.parkType = parkType;
        this.parkingAmount = parkingAmount;
        this.weekDaysStartTime = weekDaysStartTime;
        this.weekDaysEndTime = weekDaysEndTime;
        this.weekendStartTime = weekendStartTime;
        this.weekendEndTime = weekendEndTime;
        this.basicRate = basicRate;
        this.dayTicketPrice = dayTicketPrice;
        this.specialNote = specialNote;
    }
}
