package parkSystem.park.park.controller.dto.response;

import parkSystem.park.park.domain.ParkingInfo;


public record ParkingAllInfoResDTO (
        String pkNam,    // 주차장명

        String jibunAddr, // 주차장 주소

        Integer pkCnt,     // 주차장 댓수

        String pkFm)   // 주차장 유형
         {

    public static ParkingAllInfoResDTO toDto(ParkingInfo parkingInfo){
        return new ParkingAllInfoResDTO(
                parkingInfo.getParkingName(), parkingInfo.getParkingAddress(), parkingInfo.getParkingAmount(), parkingInfo.getParkType());
    }

}
