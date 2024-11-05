package parkSystem.park.park.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import parkSystem.park.park.dto.response.ParkingAllInfoResDTO;
import parkSystem.park.park.dto.response.ParkingInfoResDTO;
import parkSystem.park.park.service.query.ParkingInfoQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingInfoFacade {

    private final ParkingInfoQueryService parkingInfoQueryService;

    /**
     *
     * 전체 주차장 목록 조회
     */
    public List<ParkingAllInfoResDTO> findAllParkingInfo() {
        return parkingInfoQueryService.parkList();
    }

    /**
     *
     * @ 세부 주차장 내용 조회
     * @return
     */
    public ParkingInfoResDTO findBypParkingId(Long parkingId){
        return parkingInfoQueryService.parkInfo(parkingId);
    }












}
