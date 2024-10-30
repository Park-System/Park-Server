package parkSystem.park.park.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.park.domain.ParkingInfo;
import parkSystem.park.park.dto.response.ParkingAllInfoResDTO;
import parkSystem.park.park.dto.response.ParkingInfoResDTO;
import parkSystem.park.park.repository.ParkingInfoRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ParkingInfoQueryServiceImplTest {

    @Autowired
    private ParkingInfoQueryService parkQueryService;

    @Autowired
    private ParkingInfoRepository parkInfoRepository;

    private ParkingInfo saveParkingInfo;

    @BeforeEach
    void setUp() {
        saveParkingInfo = parkInfoRepository.save(new ParkingInfo(
                "Downtown Parking",         // parkingName
                "123 Main St, Cityville",   // parkingAddress
                "Public",                   // parkType
                100,                        // parkingAmount
                "08:00",                    // weekDaysStartTime
                "20:00",                    // weekDaysEndTime
                "09:00",                    // weekendStartTime
                "22:00",                    // weekendEndTime
                "20.00",                    // dayTicketPrice
                "1000"
        ));



    }


    @Test
    @DisplayName("전체 주차장 정보 조회 서비스")
    public void parkAll() throws Exception {
       //given
        parkInfoRepository.save(new ParkingInfo(
                "Downtown Parking2",         // parkingName
                "123 Main St, Cityville2",   // parkingAddress
                "Public",                   // parkType
                100,                        // parkingAmount
                "08:00",                    // weekDaysStartTime
                "20:00",                    // weekDaysEndTime
                "09:00",                    // weekendStartTime
                "22:00",                    // weekendEndTime
                "20.00",                    // dayTicketPrice
                "1000"
        ));

       //when
        List<ParkingAllInfoResDTO> parkAllInfoResDTOS = parkQueryService.parkList();


        //then
        assertThat(parkAllInfoResDTOS.size()).isEqualTo(2);

    }


    @Test
    @DisplayName("주차장 아이디로 세부 주차장 정보 조회")
    public void findB() throws Exception {
       //given

       //when
        ParkingInfoResDTO parkingInfoResDTO = parkQueryService.parkInfo(saveParkingInfo.getId());

        //then
        assertThat(parkingInfoResDTO.pkNam()).isEqualTo("Downtown Parking");
        assertThat(parkingInfoResDTO.pkCnt()).isEqualTo(100);
    }

}