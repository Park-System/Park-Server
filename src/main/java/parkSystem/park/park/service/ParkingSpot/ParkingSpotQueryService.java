package parkSystem.park.park.service.ParkingSpot;

import parkSystem.park.park.dto.response.ParkingSpotResDTO;

import java.util.List;

public interface ParkingSpotQueryService {

    List<ParkingSpotResDTO> findAllParkingSpots(Long parkingInfoId);
}
