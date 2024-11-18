package parkSystem.park.park.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parkSystem.park.park.controller.dto.response.ParkingSpotResDTO;
import parkSystem.park.park.service.facade.ParkingSpotFacade;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/parkingSpot")
@Tag(name = "ParkingSpot", description = "ParkingSpot API")
public class ParkingSpotController {

    private final ParkingSpotFacade parkingSpotService;


    @GetMapping("/{parkingInfoId}")
    public ResponseEntity<List<ParkingSpotResDTO>> parkingSpot(@PathVariable("parkingInfoId") Long parkingInfoId) {
        List<ParkingSpotResDTO> parkingSpotList = parkingSpotService.findParkingSpotList(parkingInfoId);


        return ResponseEntity.ok(parkingSpotList);

    }

}
