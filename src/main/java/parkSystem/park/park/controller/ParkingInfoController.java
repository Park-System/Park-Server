package parkSystem.park.park.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parkSystem.park.park.dto.response.ParkingAllInfoResDTO;
import parkSystem.park.park.dto.response.ParkingInfoResDTO;
import parkSystem.park.park.service.Facade.ParkingService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Parking", description = "Parking API")
@RequestMapping("/parking")
public class ParkingInfoController {

    private final ParkingService parkingService;


    @GetMapping
    public ResponseEntity<List<ParkingAllInfoResDTO>> getParkingAll() {
        List<ParkingAllInfoResDTO> parkingAllInfoResDTOS = parkingService.findAllParkingInfo();

        return ResponseEntity.ok(parkingAllInfoResDTOS);
    }

    @GetMapping("/{parkingId}")
    public ResponseEntity<ParkingInfoResDTO> getParkingInfo(@PathVariable Long parkingId) {
        ParkingInfoResDTO parkingInfoResDTO = parkingService.findBypParkingId(parkingId);

        return ResponseEntity.ok(parkingInfoResDTO);
    }



}
