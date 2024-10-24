package parkSystem.park.park.dto.response;

import lombok.Data;
import lombok.Getter;

@Getter
public class ParkInfoApiResDTO {
    String pkNam;     // 주차장명
    String jibunAddr; // 주차장 주소
    Integer pkCnt;     // 주차장 댓수
    String pkFm;      // 주차장 유형
    String svcSrtTe;  // 평일 시작 시간
    String svcEndTe;  // 평일 종료 시간
    String satSrtTe;  // 토요일 시작 시간
    String satEndTe;  // 토요일 종료 시간
    String temMin;    // 주차 기본 요금
    String ftDay;     // 1일 주차권 요금
    String spclNote;
}
