package parkSystem.park.park.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.park.domain.ParkingInfo;
import parkSystem.park.park.repository.ParkInfoRepository;

@SpringBootTest
@Transactional
public class ParkRepositoryTest {

    @Autowired
    private ParkInfoRepository parkInfoRepository;


    @Test
    @DisplayName("h2 데이터베이스를 테스트를 위한 테스트")
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
                "5.00",                     // basicRate
                "20.00",                    // dayTicketPrice
                "Free on Sundays"           // specialNote
        );

       //when
        parkInfoRepository.save(parkingInfo);

       //then
        Assertions.assertThat(parkInfoRepository.findAll()).hasSize(1);
    }
}
