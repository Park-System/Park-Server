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
import parkSystem.park.park.service.Facade.ParkingInfoService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "ParkingInfo", description = "ParkingInfo API")
@RequestMapping("/parking")
public class ParkingInfoController {

    private final ParkingInfoService parkingService;


    @GetMapping
    public ResponseEntity<List<ParkingAllInfoResDTO>> getParkingAll() {
        log.info("주차장 모두 조회");
        List<ParkingAllInfoResDTO> parkingAllInfoResDTOS = parkingService.findAllParkingInfo();

        return ResponseEntity.ok(parkingAllInfoResDTOS);
    }

    @GetMapping("/{parkingId}")
    public ResponseEntity<ParkingInfoResDTO> getParkingInfo(@PathVariable Long parkingId) {
        log.info("해당 주차 세부내용");
        ParkingInfoResDTO parkingInfoResDTO = parkingService.findBypParkingId(parkingId);

        return ResponseEntity.ok(parkingInfoResDTO);
    }




}
