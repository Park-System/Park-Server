package parkSystem.park.park.dto.response;

import lombok.Getter;
import parkSystem.park.park.domain.ParkingInfo;

@Getter
public class ParkingAllInfoResDTO {

    String pkNam;     // 주차장명

    String jibunAddr; // 주차장 주소

    Integer pkCnt;     // 주차장 댓수

    String pkFm;      // 주차장 유형

    public ParkingAllInfoResDTO(ParkingInfo parkingInfo) {
        this.pkNam = parkingInfo.getParkingName();
        this.pkCnt = parkingInfo.getParkingAmount();
        this.jibunAddr = parkingInfo.getParkingAddress();
        this.pkFm =  parkingInfo.getParkType();
    }
}
