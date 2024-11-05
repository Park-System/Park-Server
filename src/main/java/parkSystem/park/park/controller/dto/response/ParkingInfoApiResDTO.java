package parkSystem.park.park.controller.dto.response;

import parkSystem.park.park.domain.ParkingInfo;

public record ParkingInfoApiResDTO(
        String pkNam,  // 주차장명
        String jibunAddr, // 주차장 주소
        Integer pkCnt,     // 주차장 댓수
        String pkFm,      // 주차장 유형
        String svcSrtTe,  // 평일 시작 시간
        String svcEndTe,  // 평일 종료 시간
        String satSrtTe,  // 토요일 시작 시간
        String satEndTe,  // 토요일 종료 시간
        String ftDay,     // 1일 주차권 요금
        String deposit // 보증금
) {

    public static ParkingInfoApiResDTO toDto(ParkingInfo parkingInfo) {
        return new ParkingInfoApiResDTO(
                parkingInfo.getParkingName(),
                parkingInfo.getParkingAddress(),
                parkingInfo.getParkingAmount(),
                parkingInfo.getParkType(),
                parkingInfo.getWeekDaysStartTime(),
                parkingInfo.getWeekDaysEndTime(),
                parkingInfo.getWeekendStartTime(),
                parkingInfo.getWeekDaysEndTime(),
                parkingInfo.getDayTicketPrice(),
                parkingInfo.getDeposit()
        );
    }




}
