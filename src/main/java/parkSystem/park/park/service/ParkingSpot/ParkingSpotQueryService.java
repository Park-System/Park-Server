package parkSystem.park.park.service.ParkingSpot;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.common.config.exception.NotFoundException;
import parkSystem.park.park.domain.ParkingSpot;
import parkSystem.park.park.dto.response.ParkingSpotResDTO;
import parkSystem.park.park.repository.ParkingSpotRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class ParkingSpotQueryService  {

    private final ParkingSpotRepository parkingSpotRepository;


    public List<ParkingSpotResDTO> findAllParkingSpots(Long parkingInfoId) {
        List<ParkingSpot> findByList = parkingSpotRepository.findByParkingInfoId(parkingInfoId).orElseThrow(() -> new NotFoundException("해당 주차장은 없습니다"));

        return findByList.stream().map(ParkingSpotResDTO::toDTO).toList();

    }
}
