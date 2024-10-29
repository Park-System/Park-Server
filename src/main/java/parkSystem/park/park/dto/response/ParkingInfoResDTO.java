package parkSystem.park.park.dto.response;

import lombok.Getter;
import parkSystem.park.park.domain.ParkingInfo;

@Getter
public class ParkingInfoResDTO {

    String pkNam;     // 주차장명
    String jibunAddr; // 주차장 주소
    Integer pkCnt;     // 주차장 댓수
    String pkFm;      // 주차장 유형
    String svcSrtTe;  // 평일 시작 시간
    String svcEndTe;  // 평일 종료 시간
    String satSrtTe;  // 토요일 시작 시간
    String satEndTe;  // 토요일 종료 시간
    String ftDay;     // 1일 주차권 요금
    String deposit; // 보증금

    public ParkingInfoResDTO(ParkingInfo parkingInfo) {
        this.pkNam = parkingInfo.getParkingName();
        this.jibunAddr = parkingInfo.getParkingAddress();
        this.pkCnt = parkingInfo.getParkingAmount();
        this.pkFm = parkingInfo.getParkType();
        this.svcSrtTe = parkingInfo.getWeekDaysStartTime();
        this.svcEndTe = parkingInfo.getWeekDaysEndTime();
        this.satSrtTe = parkingInfo.getWeekendStartTime();
        this.satEndTe = parkingInfo.getWeekendEndTime();
        this.ftDay = parkingInfo.getDayTicketPrice();
        this.deposit = parkingInfo.getDeposit();
    }
}
