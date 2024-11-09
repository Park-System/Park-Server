package parkSystem.park.park.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.park.domain.ParkingInfo;
import parkSystem.park.park.repository.ParkingInfoRepository;

@SpringBootTest
@Transactional
public class ParkingRepositoryTest {

    @Autowired
    private ParkingInfoRepository parkInfoRepository;


    @Test
    @DisplayName("h2 데이터베이스를 테스트를 위한 테스트f")
    public void test() throws Exception {
        //given
        ParkingInfo parkingInfo = new ParkingInfo(
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
        );

        //when
        parkInfoRepository.save(parkingInfo);

        //then
        Assertions.assertThat(parkInfoRepository.findAll()).hasSize(1);
    }
}