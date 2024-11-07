package parkSystem.park.reservation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parkSystem.park.reservation.controller.dto.ReservationReqDTO;
import parkSystem.park.reservation.controller.dto.ReservationResDTO;
import parkSystem.park.reservation.service.facade.ReservationService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;


    @PostMapping()
    public ResponseEntity<ReservationResDTO> reservation(@RequestBody ReservationReqDTO reservationReqDTO){

        log.info("컨트롤러 요청옴");

        log.info(reservationReqDTO.toString());

        ReservationResDTO reservation = reservationService.reservation(reservationReqDTO);

        return ResponseEntity.ok(reservation);
    }

}
