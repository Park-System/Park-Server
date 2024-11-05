package parkSystem.park.park.service.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.park.domain.ParkingInfo;
import parkSystem.park.park.domain.ParkingSpot;
import parkSystem.park.park.domain.enums.ParkingType;
import parkSystem.park.park.dto.response.ParkingSpotResDTO;
import parkSystem.park.park.repository.ParkingInfoRepository;
import parkSystem.park.park.repository.ParkingSpotRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional

class ParkingSpotServiceTest {

    @Autowired
    private ParkingSpotFacade parkingSpotService;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private ParkingInfoRepository parkingInfoRepository;

    private ParkingInfo parkingInfo;


    @BeforeEach
    @Rollback(value = false)
    void setUp() {
                parkingInfo = ParkingInfo.builder()
                .parkingName("테스트 주차장1")
                .parkingAddress("서부시")
                .parkingAmount(100)
                .build();

        parkingInfoRepository.save(parkingInfo);

        ParkingSpot parkingSpot1 = new ParkingSpot(ParkingType.NORMAL, "자리 1", parkingInfo);
        ParkingSpot parkingSpot2 = new ParkingSpot(ParkingType.NORMAL, "자리 2", parkingInfo);

        parkingSpotRepository.save(parkingSpot1);
        parkingSpotRepository.save(parkingSpot2);

    }


    @Test
    @DisplayName("주차장 아이디로 해당 주차장에 속하는 주차자리 조회")
    public void parkingSpotTest() throws Exception {
       //given
        List<ParkingSpotResDTO> parkingSpotList = parkingSpotService.findParkingSpotList(parkingInfo.getId());


       //then
        assertThat(parkingSpotList.size()).isEqualTo(2);
    }






}