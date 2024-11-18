package parkSystem.park.park.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static parkSystem.park.park.ParkingConst.PARKING_AMOUNT_EXCEPTION;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

public class ParkingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_info_id")
    private Long id;

    private String parkingName; //주자창 이름

    private String parkingAddress; // 주차 주소

    private String parkType; // 주차 타입

    private int parkingAmount; // 현재 주차 숫자

    private String weekDaysStartTime; //주차 평일 시작 시간

    private String weekDaysEndTime;  //주차 평일 마감 시간

    private String weekendStartTime; //주차 주말 시작 시간

    private String weekendEndTime; // 주자 마감 시간

    private String dayTicketPrice; // 당일 티켓 요금

    private String deposit; // 보증금

    @Builder
    public ParkingInfo(String parkingName, String parkingAddress, String parkType, int parkingAmount, String weekDaysStartTime, String weekDaysEndTime, String weekendStartTime, String weekendEndTime,  String dayTicketPrice, String deposit) {
        this.parkingName = parkingName;
        this.parkingAddress = parkingAddress;
        this.parkType = parkType;
        this.parkingAmount = parkingAmount;
        this.weekDaysStartTime = weekDaysStartTime;
        this.weekDaysEndTime = weekDaysEndTime;
        this.weekendStartTime = weekendStartTime;
        this.weekendEndTime = weekendEndTime;
        this.dayTicketPrice = dayTicketPrice;
        this.deposit = deposit;
    }

    public void decreaseParkingAmount() {
        int result = --parkingAmount;
        if(result < 0) {
            throw new IllegalArgumentException(PARKING_AMOUNT_EXCEPTION);
        }
        this.parkingAmount = result;
    }
}
