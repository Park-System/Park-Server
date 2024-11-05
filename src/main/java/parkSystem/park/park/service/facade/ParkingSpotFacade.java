package parkSystem.park.park.service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import parkSystem.park.park.controller.dto.response.ParkingSpotResDTO;
import parkSystem.park.park.service.query.ParkingSpotQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingSpotFacade {

    private final ParkingSpotQueryService parkingSpotQueryService;


    /**
     * 주차장 칸 조회
     */
    public List<ParkingSpotResDTO> findParkingSpotList(Long parkingInfoId){
        return parkingSpotQueryService.findAllParkingSpots(parkingInfoId);
    }
}
