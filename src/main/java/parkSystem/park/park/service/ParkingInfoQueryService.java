package parkSystem.park.park.service;

import parkSystem.park.park.dto.response.ParkingAllInfoResDTO;
import parkSystem.park.park.dto.response.ParkingInfoResDTO;

import java.util.List;

public interface ParkingInfoQueryService {

    List<ParkingAllInfoResDTO> parkList();

    ParkingInfoResDTO parkInfo(Long parkInfoId);




}
