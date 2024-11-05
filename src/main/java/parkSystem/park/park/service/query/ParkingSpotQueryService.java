package parkSystem.park.park.service.query;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.common.config.exception.NotFoundException;
import parkSystem.park.park.domain.ParkingSpot;
import parkSystem.park.park.controller.dto.response.ParkingSpotResDTO;
import parkSystem.park.park.repository.ParkingSpotRepository;

import java.util.List;

import static parkSystem.park.park.ParkingConst.PARKING_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class ParkingSpotQueryService  {

    private final ParkingSpotRepository parkingSpotRepository;


    public List<ParkingSpotResDTO> findAllParkingSpots(Long parkingInfoId) {
        List<ParkingSpot> findByList = parkingSpotRepository.findByParkingInfoId(parkingInfoId).orElseThrow(() -> new NotFoundException(PARKING_NOT_FOUND));

        return findByList.stream().map(ParkingSpotResDTO::toDTO).toList();

    }

    public ParkingSpot findParkingSpotById(Long parkingSpotId) {
        return parkingSpotRepository.findById(parkingSpotId).orElseThrow(() -> new NotFoundException(PARKING_NOT_FOUND));
    }
}
